# Micronaut version of the comicbook application.

## Step 3 : Comicbook Controller

If you want to start this step, checkout the step3-init branch of the project.

```shell
$ git checkout step3-init
```

We add a controller on Comicbook service and a unit test to test this controller.

* [The controller](#the_controller)
* [Test the endpoint](#test_the_endpoint)
* [Build, run and test](#build,_run_and_test)
* [Build and run a docker image of the service](#build_and_run_a_docker_image_of_the_service)
* [Exercice](#exercice)
* [A solution](#a_solution)

### The controller

We create a minimal controller for comicbook. We inject the repository and add an endpoint to get the list of comicbooks.

```java
@Controller("/comicbook/v1/comicbooks")
public class ComicbookController {

    private ComicbookRepository comicbookRepository;

    public ComicbookController(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Comicbook>> list() {
        return HttpResponse.ok(comicbookRepository.list().blockingGet());
    }
}
```
### Test the endpoint

A unit test is created to test the endpoint. When we add the feature 

```java
@MicronautTest
public class ComicbookControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject @Client("/comicbook/v1/comicbooks")
    HttpClient client;

    @Test 
    void emptyList() throws Exception {
        String retrieve = client.toBlocking().retrieve(HttpRequest.GET(""), String.class);
        assertNotNull(retrieve);
        assertEquals("[]", retrieve);
    }
}
```
You can run this test by Maven or in your IDE. The test succeed.

### Build, run and test

Build and run the service with your IDE, or with shell commands:
```shell
$ mvn clean install
$ java -jar target/comicbook-0.1.jar
```

You can test the endpoint with Postman, or with shell command:

```shell
$ curl -X GET \
  http://localhost:7001/comicbook/v1/comicbooks \
  -H 'Accept: application/json' \
  -H 'Host: localhost:7001' 
```

We received a HTTP response 200 with a content (a list empty or not of comicbook). Stop the server.

### Build and run a docker image of the service

Now we build a docker image that contains the native image of the service. We use GraalVM to build this natice image.

When you create the project, a `Dockerfile` and a script to build the dosker image `docker-build.sh` was created. We update the first one to:

```dockerfile
FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/comicbook
WORKDIR /home/app/comicbook
RUN gu install native-image
RUN native-image --no-server -cp target/comicbook-*.jar --initialize-at-run-time=io.micronaut.configuration.mongo.reactive.test.AbstractMongoProcessFactory

FROM frolvlad/alpine-glibc
EXPOSE 7001
COPY --from=graalvm /home/app/comicbook .
ENTRYPOINT ["./comicbook"]
```

We change:
* The port of the service.
* The command to build the native image to use MongoDB feature.

We can build the image with command:

```shell
$ ./docker-build.sh
```

In `/docker` directory we have a file named `comicbook.yaml`. It's a docker-compose file to run the comicbook service, a MongoDB service and a MOngoDB initializer service to create the user on the MongoDB.

Run this docker-compose:

```shell
$ docker-compose -f comicbook.yaml up -d
```
And now you can call again the service with Curl:

```shell
$ curl -X GET \
  http://localhost:7001/comicbook/v1/comicbooks \
  -H 'Accept: application/json' \
  -H 'Host: localhost:7001' 
```

### Exercice

Now you can add a new endpoint like a POST of one resource. Annotation for a POST endpoint is `@Post` and annotation to get the content of the request is `@Body`.

### A solution

Checkout the branch step3-final.

```shell
$ git checkout step3-final
```