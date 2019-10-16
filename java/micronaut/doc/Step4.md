# Micronaut version of the comicbook application.

## Step 4 : API call other services

If you want to start this step, checkout the step4-init branch of the project.

```shell
$ git checkout step4-init
```

In our use case the service API call other services to have only one service in front of the application. For this we choose to use a Java service like others to use the Micronaut HTTP client, and to apply "Operations" method.

* [Operations](#operations)
* [Update the controller](#update_the_controller)
* [The HTTP client](#The_HTTP_client)
* [Exercice](#exercice)

### Operations

To use the same definition when we declare the endpoints use the controller class, and the endpoints use by the HTTP client we describe them into a interface. Following we describe this interface for the comicbook service.

```java
public interface ComicbookOperations {

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    HttpResponse<List<Comicbook>> list();

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> get(@PathVariable String id);

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> post(@Body Comicbook person);

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook);

    @Delete(value = "/{id}")
    HttpResponse delete(@PathVariable String id);
}
```

### Update the controller

This interface is implemented by the controller, so we juste remove endpoints description from the controller.

```java
@Controller("/comicbook/v1/comicbooks")
public class ComicbookController implements ComicbookOperations {

    private ComicbookRepository comicbookRepository;

    public ComicbookController(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    @Override
    public HttpResponse<List<Comicbook>> list() {
        return HttpResponse.ok(comicbookRepository.list().blockingGet());
    }

    @Override
    public HttpResponse<Comicbook> get(@PathVariable String id) {
        Comicbook comicbook = comicbookRepository.get(id).blockingGet();
        if (comicbook != null) {
            return HttpResponse.ok(comicbook);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Override
    public HttpResponse<Comicbook> post(@Body Comicbook person) {
        return HttpResponse.created(comicbookRepository.create(person).blockingGet());
    }

    @Override
    public HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook) {
        return HttpResponse.ok(comicbookRepository.update(comicbook).blockingGet());
    }

    @Override
    public HttpResponse delete(@PathVariable String id) {
        comicbookRepository.delete(id);
        return HttpResponse.noContent();
    }
}
```

### The HTTP client

And to create a client, we just need to extends the operations interface.

```java
@Client(id = "comicbook", path = "/comicbook/v1/comicbooks")
public interface ComicbookClient extends ComicbookOperations {

}
```

The Client annotation declare a client, that use configuration describe in comicbook section in service configuration and a path. We inject this client in an API controller to redirect call to comicbook service.

```java
@Controller("/api/v1/comicbooks/")
public class APIComicbookController {

    private final ComicbookClient client;

    public APIComicbookController(ComicbookClient client) {
        this.client = client;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Comicbook>> list() {
        return client.list();
    }
}	
```

And configure the client.

```yaml
  http:
    services:
      comicbook:
        urls:
          - http://localhost:7001/
```

### Exercice

Run the both services, Comicbook (port 7001) and API (port 7000), and test the API endpoint.

```shell
$ curl -X GET \
  http://localhost:7000/api/v1/comicbooks
```

It returns the same response as the Comicbook endpoint.

```shell
curl -X GET \
  http://localhost:7001/comicbook/v1/comicbooks
```

And now, add the API endpoint to POST a comicbook.

### A solution

Checkout the branch step4-final.

```shell
$ git checkout step4-final
```

