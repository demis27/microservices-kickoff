# Quarkus version of the comicbook application

## Step 1 : Installation and project creation

* [Initialise the project](#initialise-the-project)
* [Build the project](#build-the-project)
* [Run each service](#run-each-service)
* [Build and run a native application](#build-and-run-a-native-application)

### Initialise the project

The Quarkus Cli is not currently available. I create via maven three project, comicbook, person and api and assemble them as module of the backend maven project. I add by IntelliJ the common module.

### Build the project

Build by Intellij or by maven:

```shell
microservices-kickoff/java/quarkus/backend> mvn clean install
```

### Run each service

You can run each service by maven, for exemple for the comicbook service:

```shell
microservices-kickoff/java/quarkus/backend/comicbook> mvn compile quarkus:dev:
```

You can check the controller with Curl:

```shell
curl -X GET http://localhost:8080/comicbook/v1/comicbooks
```

### Build and run a native application

I install GraalVM with SDKMan, so I set the GRAALVM_HOME with commande:

```shell
export GRAALVM_HOME=/home/skermabon/.sdkman/candidates/java/19.2.0-grl/
```

And we install the native-image tools with the command:

```shell
${GRAALVM_HOME}/bin/gu install native-image
```

and know we can build a native image of each service, for comicbook we use:

```shell
microservices-kickoff/java/quarkus/backend/comicbook> mvn package -Pnative
```

That build a native image in target folder. You can launch it with:

```shell
microservices-kickoff/java/quarkus/backend/comicbook/target> ./comicbook-1.0-SNAPSHOT-runner
```

### Build docker image with the native image

```shell
microservices-kickoff/java/quarkus/backend/comicbook> docker build -f src/main/docker/Dockerfile.native -t kickoff-quarkus/comicbook .
```

