package com.example.mongodbconsoleapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mongodbconsoleapp.model.Person;
import com.example.mongodbconsoleapp.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootApplication
public class MongoDBConsoleApp implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDBConsoleApp.class, args);
    }

    public void bulkLoadPeople() {
        List<Person> batch = new ArrayList<Person>();
        final int nDocs = 300_000; // 300,000 is about 1GB of JSON
        for (int personNo = 0; personNo < nDocs; personNo++) {
            Person person = new Person();
            batch.add(person);
            if (batch.size() == 1000) {
                personRepository.saveAll(batch);
                System.out.println("Loaded " + (personNo + 1) + " of " + nDocs);
                batch = new ArrayList<Person>();
            }
        }
        if (batch.size() > 0) {
            personRepository.saveAll(batch);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        long count = personRepository.count();
        boolean useRaw = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("true")) {
                useRaw = true;
            }
        }

        // If we find none then we need to generate sample data
        if (count == 0) {
            System.out.println("\n\n\n-------------\nNo data found - making some, this will take a long time.");
            bulkLoadPeople();
            System.exit(0);
        }
        System.out.println("Fetching " + count + " person records to JSON");
        ;
        long startTime = System.nanoTime();

        if (useRaw == false) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            try (Stream<Person> personStream = personRepository.findAllByStream().stream();) {
                personStream.forEach(person -> {
                    try {
                        String json = objectWriter.writeValueAsString(person);
                        // Use json so not optimized out
                        if (json.length() < 1) {
                            System.exit(1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            try (Stream<String> personStream = personRepository.findRawJsonStream().stream();) {

                personStream.forEach(person -> {
                    // Just so we are using the value
                    if (person.length() < 1) {
                        System.exit(1);
                    }

                });
            }
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        duration = duration / (1000 * 1000);

        System.out.println("Execution time: " + duration + " milliseconds");
    }
}
