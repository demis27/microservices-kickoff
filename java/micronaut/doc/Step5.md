# Micronaut version of the comicbook application.

## Step 5 - Messages between microservices

If you want to start this step, checkout the step5-init branch of the project.

```shell
$ git checkout step5-init
```

* [Introduction](#introduction)
* [Configuration](#configuration)
* [Produce the message](#produce-the-message)
* [Consume the message](#consume-the-message)
* [Exercice](#exercice)


### Introduction

We complete the comicbook document by adding persons who contributed to this comicbook as author, artist, penciller, letterer, etc... When a person document changes, the person service send a message to the comicbook service to update all documents that contains this person.

For that we use Kafka for communication between microservices. We produce a message in the person service, and consume this message on comicbook service.

A comicbook document look like:

```json
{
    "_id" : "b9f60ef3-2497-40c7-b96d-b5ebe2f6d9d9",
    "name" : "Northstar",
    "persons" : {
        "WRITER" : [ 
            {
                "_id" : "f09a81db-298c-46b9-a893-465d9fbf781c",
                "firstname" : "Chris",
                "lastname" : "Claremont"
            }
        ]
    }
}
```

A person document look like:

```json
{
    "id": "f09a81db-298c-46b9-a893-465d9fbf781c",
    "firstname": "Chris",
    "lastname": "Claremont"
}
```

### Configuration

We enable kafka in the both services in application.xml:

```yaml
kafka:
  bootstrap:
    servers: localhost:9092
```

We use a kafka and zookeeper docker images in a docker-compose file:

```yaml
version: '3.1'
  
services:
  zookeeper:
    image: wurstmeister/zookeeper
 
  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
```

### Produce the message

We add a Kafka message producer as in interface. This producer send a kafka message with id, the id of the person, and as content the person object. We use the kafka topic `person`.

```java
@KafkaClient
public interface PersonProducer {

    @Topic("person")
    void sendPerson(@KafkaKey String id, Person person);
}
```

And we inject this producer to the controller, to send a message when we update a person.

```java
    @Override
    public HttpResponse<Person> put(@PathVariable String id, @Body Person person) {
        Person updatedPerson = personRepository.update(person).blockingGet();
        personProducer.sendPerson(updatedPerson.getId(), updatedPerson);
        return HttpResponse.ok(updatedPerson);
    }

```

### Consume the message

On the comicbook microservice, we add a listener to read messages and process them.

```java
@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class PersonListener {

    private final ComicbookService comicbookService;

    public PersonListener(ComicbookService comicbookService) {
        this.comicbookService = comicbookService;
    }

    @Topic("person")
    public void receivePerson(@KafkaKey String id, Person updatedPerson) {
        comicbookService.updatePerson(updatedPerson);
    }
}
```

### Exercice

We will build native docker image, launch the docker-compose and test a comicbook is updated when a person is updated.

#### Build all backend

```shell
microservices-kickoff/java/micronaut/backend> mvn clean install
```

We expected result is a successfull buid.

#### Build native image

```shell
microservices-kickoff/java/micronaut/backend/api> ./docker-build.sh
```

```shell
microservices-kickoff/java/micronaut/backend/person> ./docker-build.sh
```

```shell
microservices-kickoff/java/micronaut/backend/comicbook> ./docker-build.sh
```

We expected result is (port and service are different):

```shell
To run the docker container execute:
    $ docker run -p 8080:8080 comicbook
```

And we can check the docker images exist.

``` shell
$ docker images | grep "api\|person\|comicbook"
comicbook                latest              9702f4eb38b6        19 minutes ago      110MB
person                   latest              b924b8ef04aa        21 minutes ago      110MB
api                      latest              b30ab4edd93d        24 minutes ago      92MB
```

#### Launch the docker compose

Before launch the docker compose, stop previousvly launched container (kafka, zookeeper, etc...).

```shell
microservices-kickoff/docker> docker-compose -f comicbook.yaml up -d
```

We check the list of running containers.

```shell
CONTAINER ID        IMAGE                    COMMAND                  CREATED             STATUS              PORTS                                  NAMES
9815565a52d2        comicbook:latest         "./comicbook"            21 minutes ago      Up 21 minutes       0.0.0.0:7001->7001/tcp                 docker_comicbook_1
1b89e60d134e        person:latest            "./person"               21 minutes ago      Up 21 minutes       0.0.0.0:7002->7002/tcp                 docker_person_1
3dff43306423        wurstmeister/kafka       "start-kafka.sh"         21 minutes ago      Up 21 minutes       0.0.0.0:9092->9092/tcp                 docker_kafka_1
ec83cdee82a6        wurstmeister/zookeeper   "/bin/sh -c '/usr/sb…"   22 minutes ago      Up 21 minutes       22/tcp, 2181/tcp, 2888/tcp, 3888/tcp   docker_zookeeper_1
851f443cfda0        api:latest               "./api"                  22 minutes ago      Up 21 minutes       0.0.0.0:7000->7000/tcp, 7001/tcp       docker_api_1
7329d5521b05        mongo:latest             "docker-entrypoint.s…"   18 hours ago        Up 22 minutes       0.0.0.0:27017->27017/tcp               docker_mongo_1
```

#### Test with curl

Note: Id of resources you created are not the same as the following exemple. Please update with yours.

The scenario of this test is:
1. We create a person
2. We create a comicbook that use this person as writer of the comicbook
3. We update this person
4. We get the comicbook to check if the writer is updated

___
1. We create a person

Request
```shell
$ curl -X POST \
  http://localhost:7000/api/v1/persons/ \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
	   "firstname": "Oliver",
     "lastname": "Queen"

}'
```

Response
```json
{
  "id":"95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
  "firstname":"Oliver",
  "lastname":"Queen"
}
```

___
2. We create a comicbook with this person:

Request
```shell
$ curl -X POST \
  http://localhost:7000/api/v1/comicbooks/ \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Green Arrow",
    "persons": {
        "WRITER": [
            {
             	"id":"95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
               	"firstname":"Oliver",
               	"lastname":"Queen"
            }
        ]
    }
}'
```

Response
```json
{
    "id": "8f9ba4cd-957f-499e-8d65-b30d7c28be6a",
    "name": "Green Arrow",
    "persons": {
        "WRITER": [
            {
                "id": "95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
                "firstname": "Oliver",
                "lastname": "Queen"
            }
        ]
    }
}
```

___
3. We update the person (the lastname is change to uppercase):

Request
```shell
$ curl -X PUT \
  http://localhost:7000/api/v1/persons/95a34d8e-7ce4-4a85-bf73-dabe455fdd95 \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "id": "95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
    "firstname": "Oliver",
    "lastname": "QUEEN"
}'
```

Response
```json
{
    "id": "95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
    "firstname": "Oliver",
    "lastname": "QUEEN"
}
```

___
4. We get the updated comicbook

Request
```shell
$ curl -X GET \
  http://localhost:7000/api/v1/comicbooks/8f9ba4cd-957f-499e-8d65-b30d7c28be6a \
  -H 'Accept: application/json'
```

Response
```json
{
    "id": "8f9ba4cd-957f-499e-8d65-b30d7c28be6a",
    "name": "Green Arrow",
    "persons": {
        "WRITER": [
            {
                "id": "95a34d8e-7ce4-4a85-bf73-dabe455fdd95",
                "firstname": "Oliver",
                "lastname": "QUEEN"
            }
        ]
    }
}
```

The writer is updated.