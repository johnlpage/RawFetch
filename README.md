# Comparison of Fetching JSON with Spring Data Repository vs MongoTemplate + JSONObject

This Repository goes with the Blog Post at XXXXX.

It demonstrates how , if you need to perform an efficient extraction of data in JSON format from MongODB when using Spring Boot and Spring Data you can use lower level primitives to double your performance when performing the read operation. This is because it shortcuts multiple conversions between data type and all the allocation and deallocation associated with it.

It will auto generate data on it's first run then on subsequent runs you can test read speed.

## Build Requirements

It Requires Maven and Java 17 to build.

## Instructions

1. Download this repository.

2. Edit the file `application.properties` to contain your URI to a MongoDB cluster

3. Run the following commands in the top directory


### Fetch JSON via the Spring Boot Model (Inefficient)

```
mvn spring-boot:run -Dspring-boot.run.arguments="false"
```


### To fetch JSON via JSONDocument (Efficient) 

```
mvn spring-boot:run -Dspring-boot.run.arguments="true"
```

Time taken will be shown in the screen output.
