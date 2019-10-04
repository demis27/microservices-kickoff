# Micronaut version of the comicbook application.

This step contains requirements and somme initialisation for the project.

## Step 0 : Requirements
* Install [SDKMan](https://sdkman.io/)


* Use SDKMan to install a JDK 8 (`sdk install java 8.0.222-zulu), or use your own JDK 8.
* Use SDKMan to install a Apache Maven 3.6 ( `sdk install maven 3.6.2`) , or use your own Maven 3.6.
* Use SDKMan to install GraalVM 19.1 (`sdk install java 19.2.0-grl`)
* Curl, Git, Intellij or another IDE, Postman or other tools to test API, docker and docker-compose.
* A Mongodb (installed or in a docker image) with an access.

## Use Mongodb as a docker image.

You can use this docker-compose file to have a Mongodb on docker (/docker/mongo.yaml).

```docker
version: '3.1'
services:
  mongo:
    image: mongo
    ports:
      - "27017:27017"
```

You launch this docker-compose file with the command:

```shell
$ cd docker
$ docker-compose -f mongo.yaml up -d
```

## Set a user on Mongodb for the project
