package com.example.mongodbconsoleapp.repository;

import org.bson.RawBsonDocument;
import org.bson.codecs.JsonObjectCodec;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonObject;
import org.bson.json.JsonWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.mql.MqlValue;
import static com.mongodb.client.model.mql.MqlValues.current;
import static com.mongodb.client.model.Projections.*;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

@Repository
public class RawPersonRepositoryImpl implements RawPersonRepository {

    @Autowired
    private MongoClient mongoClient;


    public Streamable<Stream<String>> findRawJsonStream() {

        JsonWriterSettings settings = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
        .objectIdConverter((value, writer) -> writer.writeString(value.toHexString()))
        .decimal128Converter((value, writer) -> writer.writeNumber(value.toString()))
        .dateTimeConverter( (value, writer) -> { 
           ZonedDateTime zonedDateTime = Instant.ofEpochMilli(value).atZone(ZoneOffset.UTC);
           writer.writeString(DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime));})
        .build();
        

        MongoDatabase database = mongoClient.getDatabase("bank");
        MongoCollection<JsonObject> jsonPeople = database.getCollection("person", JsonObject.class)
                .withCodecRegistry(fromProviders(fromCodecs(new JsonObjectCodec(settings)),
                        database.getCodecRegistry()));

        Bson mongoQuery = Filters.empty();

        // We also need to implement anything out Mappings in Spring are doing

        MqlValue renamedId = current().getField("_id");

        Bson projection = fields(exclude("_id"),
                computed("id", renamedId));

        MongoCursor<JsonObject> cursor = jsonPeople.find(mongoQuery).projection(projection).iterator();

        Stream<String> stream = StreamSupport.stream(((Iterable<JsonObject>) () -> cursor).spliterator(), false)
                .map(JsonObject::toString);

        return Streamable.of(stream);
    }
}
