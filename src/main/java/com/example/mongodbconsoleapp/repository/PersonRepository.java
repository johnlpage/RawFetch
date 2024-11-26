package com.example.mongodbconsoleapp.repository;

import com.example.mongodbconsoleapp.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.util.Streamable;

public interface PersonRepository extends MongoRepository<Person, String>, RawPersonRepository {

    @Query("{}")
    Streamable<Person> findAllByStream();
    
    @Query("{}")
    Streamable<String> findRawJsonStream();

}
