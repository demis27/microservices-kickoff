# microservices-kickoff

Because I love played with new frameworks, and I want to share with you my explorations, I created with project to compare Java framework to make micro-services and serverless application.
This project have two goals:
* Give you a little workshop for each framework
* Compare all java frameworks to do micro services and serverless application.

The first framework tested is Micronaut https://micronaut.io/. For each framework you can play with a small workshop.

* [Use case](#use-case)
* [Workshop steps](#workshop-steps)
* [Micronaut implementation](#micronaut-implementation)

## Use case

To test the frameworks I develop with them the same following use case. This use case is named Comicbook. It's not a "production" use case, but a use case to tests all technologies used in the micro services world.

  ![Use case](/doc/images/usecase.svg)

### API service
In this use case, the user call by HTTP the API service endpoints that redirect calls to the right service (Person or Comicbook). For example to get all comicbooks we use the endpoint `GET /api/v1/comicbooks` redirected to the Comicbook service endpoint `GET /comicbook/v1/comicbooks`.

### Comicbook service
The comicbook service manage a comicbook issue stored in mongoDB. Following, a example of a comicbook payload.

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
        ],
        "ARTIST" : [ 
            {
                "_id" : "67a4980e-e0d4-4355-be1d-96b5cd67064e",
                "firstname" : "John",
                "lastname" : "Byrne"
            }
        ]
    }
}
```

### Person service  
The person service manage a person stored in mongoDB. Person represent people who contributed in the creation of a comicbook. Following, a example of a person payload.

```json
{
    "id": "f09a81db-298c-46b9-a893-465d9fbf781c",
    "firstname": "Chris",
    "lastname": "Claremont"
}
```

A person may be a element of a comicbook document. When we update a person, we send a kafka message to the comicbook service. This message is use to update all documents that reference this person. 

## Workshop steps

All workshops are based on this use case, and each steps of workshops are based on the same part of this use case.

* Step 0: Requirement
* Step 1: Installation and project creation
* Step 2: Pojo and repository
* Step 3: Comicbook controller
* Step 4: Api controller
* Step 5: Messages between microservices

## Micronaut implementation

The micronaut implementation is available in `/java/micronaut/backend` and documentation for the micronaut workshop is available in `/java/micronaut/doc`:

* [Step 0: Requirements](/java/micronaut/doc/Step0.md)# microservices-kickoff

* [Introduction](#introduction)
* [Use case](#use-case)
* [Workshop steps](#workshop-steps)
* [Implementations](#implementation)
  * [Micronaut implementation](#micronaut-implementation)
  * [Quarkus implementation](#quarkus-implementation)
  * [Springboot implementation](#springboot-implementation)
* [Compare frameworks](#compare-frameworks)
  * [Docker images size](#docker-images-size)

## Introduction

Because I love played with new frameworks, and I want to share with you my explorations, I created this project to compare Java framework to make micro-services and serverless application.
This project have two goals:

* Give you a little workshop for each framework
* Compare all java frameworks to do micro services and serverless application.

The first framework tested is [Micronaut](https://micronaut.io/){:target="_blank"}. For each framework you can play with a small workshop.

## Use case

To test the frameworks I develop with them the same following use case. This use case is named Comicbook. It's not a "production" use case, but a use case to tests all technologies used in the micro services world.

  ![Use case](/doc/images/usecase.svg)

### API service

In this use case, the user call by HTTP the API service endpoints that redirect calls to the right service (Person or Comicbook). The API service is an handmade API Gateway. For example to get all comicbooks we use the API endpoint `GET /api/v1/comicbooks` redirected to the Comicbook service endpoint `GET /comicbook/v1/comicbooks`.

### Comicbook service

The comicbook service manage a comicbook issue stored in mongoDB. Following, an example of a comicbook payload.

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
        ],
        "ARTIST" : [
            {
                "_id" : "67a4980e-e0d4-4355-be1d-96b5cd67064e",
                "firstname" : "John",
                "lastname" : "Byrne"
            }
        ]
    }
}
```

### Person service

The person service manage a person stored in mongoDB. Person represent people who contributed to the creation of a comicbook. Following, an example of a person payload.

```json
{
    "id": "f09a81db-298c-46b9-a893-465d9fbf781c",
    "firstname": "Chris",
    "lastname": "Claremont"
}
```

A person may be a element of a comicbook document. When we update a person, we send a kafka message to the comicbook service. This message is use to update all documents that reference this person.

## Workshop steps

All workshops are based on this use case, and each steps of workshops are based on the same part of this use case.

* Step 0: Requirement
* Step 1: Installation and project creation
* Step 2: Pojo and repository
* Step 3: Comicbook controller
* Step 4: Api controller
* Step 5: Messages between microservices
* Step 6: Zipkin integration (In Progress)

## Implementations

### Micronaut implementation

The micronaut implementation is available at `/java/micronaut/backend` and documentation for the micronaut workshop is available at `/java/micronaut/doc`.

* [Step 0: Requirements](/java/micronaut/doc/Step0.md)
* [Step 1: Installation and project creation](/java/micronaut/doc/Step1.md)
* [Step 2: Pojo and repository](/java/micronaut/doc/Step2.md)
* [Step 3: Comicbook controller](/java/micronaut/doc/Step3.md)
* [Step 4: Api controller](/java/micronaut/doc/Step4.md)
* [Step 5: Messages between microservices](/java/micronaut/doc/Step5.md)
* [Step 6: Zipkin integration](/java/micronaut/doc/Step6.md) (In Progress)

### Quarkus implementation

The quarkus implementation is available at `/java/quarkus/backend` and documentation for the micronaut workshop is available at `/java/quarkus/doc`.

* [Step 0: Requirements](/java/quarkus/doc/Step0.md)
* Step 1: Installation and project creation (coming soon)
* Step 2: Pojo and repository (coming soon)
* Step 3: Comicbook controller (coming soon)
* Step 4: Api controller (coming soon)
* Step 5: Messages between microservices (coming soon)

### Springboot implementation

coming soon.

## Compare frameworks

### Docker images size

coming soon.

* [Step 1: Installation and project creation](/java/micronaut/doc/Step1.md)
* [Step 2: Pojo and repository](/java/micronaut/doc/Step2.md)
* [Step 3: Comicbook controller](/java/micronaut/doc/Step3.md)
* [Step 4: Api controller](/java/micronaut/doc/Step4.md)
* [Step 5: Messages between microservices](/java/micronaut/doc/Step5.md)
