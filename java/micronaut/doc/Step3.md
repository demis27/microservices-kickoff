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

### Build, run and test.

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

### Exercice

Now you can add a new endpoint like a POST of one resource. Annotation for a POST endpoint is `@Post` and annotation to get the content of the request is `@Body`.

### A solution

Checkout the branch step3-final.

```shell
$ git checkout step3-final
```