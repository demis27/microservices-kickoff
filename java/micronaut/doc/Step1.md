# Micronaut version of the comicbook application.

## Step 1 : Install Micronaut CLI and create the project

We install Micronaut CLI version 1.2.0 with SDKman. This application is to initiate project like the springboot initializr or the maven archetypes.

### Install Micronaut

We install Micronaut CLI with SDKMAN: 

```shell
$ sdk install micronaut 1.2.0
``` 

And we verify if Micronaut CLI is well install by the shell command:

```shell
$ mn --version
| Micronaut Version: 1.2.0
| JVM Version: 1.8.0_222
```

Micronaut CLI version 1.2.0 is install, and the default JVM version is Java 8.

### Initialise the project

We create the Micronaut project on an empty directory, we use `java/micronaut` as root directory for the Micronaut version of the project.

```shell
$ cd java/micronaut
$ mn create-federation \
--build=maven \
--lang=java \
--services=org.talend.kickoff.mn.api.api,org.talend.kickoff.mn.person.person,org.talend.kickoff.mn.common.common,org.talend.kickoff.mn.comicbook.comicbook \
--stacktrace \
backend \
--features graal-native-image,mongo-reactive,http-client
```

What this command do:
* The create-federation command create a multi maven/graddle module for a multi micro-services applications.
* We choose maven as a build tool. gradle is also available.
* We choose java as language, groovy and kotlin are also available.
* We create four modules.
* We add as features; graal-native-image,mongo-reactive,http-client to configure the `pom.xml` files with needed dependencies for their features dans some needed files.

That create a backend directory, with inside, a maven project with four modules; api, person, common and comicbook. Let's se some generated files.

On `pom.xml` file for all modules a dependency was added by the feature http-client:

```xml
    <dependency>
      <groupId>io.micronaut</groupId>
      <artifactId>micronaut-http-client</artifactId>
      <scope>compile</scope>
    </dependency>
```
On the same file mongo-reactive dependency was added by the feature mongo-reactive, and flapdoodle mongo for test:

```xml
    <dependency>
      <groupId>io.micronaut.configuration</groupId>
      <artifactId>micronaut-mongo-reactive</artifactId>
      <scope>compile</scope>
    </dependency>
[...]
    <dependency>
      <groupId>de.flapdoodle.embed</groupId>
      <artifactId>de.flapdoodle.embed.mongo</artifactId>
      <version>2.0.1</version>
      <scope>test</scope>
    </dependency>
```
And substratevm dependency was added by the feature graal-native-image:

```xml
    <dependency>
      <groupId>com.oracle.substratevm</groupId>
      <artifactId>svm</artifactId>
      <scope>provided</scope>
    </dependency>
```
