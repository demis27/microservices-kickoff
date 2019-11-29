# microservices-kickoff

This repository is done to compare Java framework to make micro-services and serverless application. The first framework tested is Micronaut https://micronaut.io/. For each framework you can play with a small workshop.

## Use case

To test the frameworks I develop the following use case with them.

  ![Use case](/doc/images/usecase.svg)

An API user call by HTTP endpoints of API service that redirect calls to the Person service and the Comicbook service. For example to get all comicbooks we use the endpoint `GET /api/v1/comicbooks` redirected to the Comicbook service endpoint `/comicbook/v1/comicbooks`.

  
All workshops are based on this use case, and each steps of workshops are based on the same part of this use case.

* Step 0: Requirement
* Step 1: Installation and project creation
* Step 2: Pojo and repository
* Step 3: Comicbook controller
* Step 4: Api controller
* Step 5: Messages between microservices

## Micronaut implementation

The micronaut implementation is available in `/java/micronaut/backend` and documentation for the micronaut workshop is available in `/java/micronaut/doc`:

* [Step 0: Requirements](/java/micronaut/doc/Step0.md)
* [Step 1: Installation and project creation](/java/micronaut/doc/Step1.md)
* [Step 2: Pojo and repository](/java/micronaut/doc/Step2.md)
* [Step 3: Comicbook controller](/java/micronaut/doc/Step3.md)
* [Step 4: Api controller](/java/micronaut/doc/Step4.md)
* [Step 5: Messages between microservices](/java/micronaut/doc/Step5.md)
