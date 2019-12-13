# Mongo

In all workshop, we use a MongoDB. To do this we use a MongoDB into docker, with an image to initiate the DB (user creation).

## Use Mongodb as a docker image

You can use this docker-compose file to have a Mongodb on docker (/docker/mongo.yaml).

```docker
version: '3.1'

services:
  mongo:
    image: mongo
    ports:
      - "27017:27017"

  mongo-init:
    image: mongo:latest
    depends_on:
      - mongo  
    command: mongo --host mongo --eval  "db.getSiblingDB("kickoff").createUser({user:'kickoff', pwd:'kickoff', roles:['readWrite', 'dbAdmin']});"
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
