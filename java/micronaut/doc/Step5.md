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
* [Run and tests](#run-and-tests)
* [Exercice](#exercice)


### Introduction

We complete the comicbook document by adding persons who contributed to this comicbook as author, artist, penciller, letterer, etc... When a person document changes, the person service send a message to the comicbook service to update all documents that contains this updated person.

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

We add a comicbook service to manage the modification of a person inside the comicbooks.

### Adapt unit test

In the controller unit test, we mock the kafka producer with Mockito and test the sendPerson method is call for a POST and a PUT.

```java
    @Inject PersonProducer personProducer;

    [...]

    @MockBean(PersonProducer.class)
    public PersonProducer personProducer() {
        return mock(PersonProducer.class);
    }

    [...]

            verify(personProducer, times(1)).sendPerson(anyString(), any(Person.class));

    [...]
```

### Run and tests

Now we can run a docker-compose of a kafka and a zookeeper docker images, present in the branch in docker directory.

```shell
$ docker-compose -f kafka.yaml
```

And we launch all services. We can add a person, with the writer role, to a comicbook by this call (change the id of the comicbook):

```shell
curl -X POST \
  'http://localhost:7001/comicbook/v1/comicbooks/b9f60ef3-2497-40c7-b96d-b5ebe2f6d9d9/writer/?=' \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{"id": "5f4cdbed-62ba-44e7-9f1e-9959277cfada", "firstname":"Jerry", "lastname": "Siegel"}'
```

If I update the person like this:

```shell
curl -X PUT \
  http://localhost:7002/person/v1/persons/5f4cdbed-62ba-44e7-9f1e-9959277cfada \
  -H 'Content-Type: application/json' \
  -d '{"id":"5f4cdbed-62ba-44e7-9f1e-9959277cfada", "firstname":"JERRY", "lastname":"SIEGEL"}'
```

Then, when I get this comicbook, the person was updated:

```shell
curl -X GET \
  http://localhost:7001/comicbook/v1/comicbooks/b9f60ef3-2497-40c7-b96d-b5ebe2f6d9d9
```

### Exercice

No Exercice in this step for the moment.
