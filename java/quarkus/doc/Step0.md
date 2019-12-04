# Quarkus version of the comicbook application.

This step contains requirements and some initialisation for the project.

## Step 0 : Requirements
* Install [SDKMan](https://sdkman.io/)
* Use SDKMan to install a JDK 8 (`sdk install java 8.0.222-zulu), or use your own JDK 8.

```shell
$ sdk install java 8.0.222-zulu
```

* Use SDKMan to install a Apache Maven 3.6 ( `sdk install maven 3.6.2`) , or use your own Maven 3.6.

```shell
$ sdk install maven 3.6.2
```

* Use SDKMan to install GraalVM 19.1 (`sdk install java 19.2.0-grl`)

```shell
$ sdk install java 19.2.0-grl
```

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

Launch mongodb on docker.

```shell
$ cd docker
$ docker-compose -f mongo.yaml up -d
```
Get the mongo container Id.

```shell
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                      NAMES
e62c39dfcb2d        mongo               "docker-entrypoint.s…"   About a minute ago   Up About a minute   0.0.0.0:27017->27017/tcp   docker_mongo_1
```

Connect on the container.

```shell
$ docker exec -ti e62c39dfcb2d mongo
> use kickoff
switched to db kickoff
> db.createUser({user: "kickoff", pwd : "kickoff", roles : ["readWrite", "dbAdmin"]})
Successfully added user: { "user" : "kickoff", "roles" : [ "readWrite", "dbAdmin" ] }
> exit
```

