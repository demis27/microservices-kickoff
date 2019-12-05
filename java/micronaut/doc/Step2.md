# Micronaut version of the comicbook application.

## Step 2 : Pojo and Repository

In this step we initiate the Pojo and a an repository for this Pojo using MongoDB reactive. Both are in the common project to be use by all micro-services. If your are not aware with MongoDB reactive, skip this step and checkout the step three branch and go to the step 3. 

```shell
$ git checkout step3
```

We add a test class to test the repository with Flapdoodle for Mongo and add a configuration class to overide the name of the database. 

* [The Pojo](#the-pojo)
* [The repository](#the-repository)
* [The repository tests](#the-repository-tests)
* [Small configuration](#small-configuration)

### The Pojo

We create an initial Pojo for a comicbook, this Pojo will be completed in the following steps.

```java
public class Comicbook {

    private String id;

    private String name;

    [...]
}
```

### The repository

A minimal repository for comicbook. We use a reactive MongoDB API and implement a simple list method.

```java
@Singleton
public class ComicbookRepository {

    public static final String COMICBOOK_COLLECTION = "comicbook";

    private final MongoClient mongoClient;

    private final ComicbookConfiguration configuration;

    public ComicbookRepository(MongoClient mongoClient, ComicbookConfiguration configuration) {
        this.mongoClient = mongoClient;
        this.configuration = configuration;
    }

    public Single<List<Comicbook>> list() {
        return Flowable.fromPublisher(getCollection().find()).toList();
    }

    [...]
    private MongoCollection<Comicbook> getCollection() {
        return mongoClient
                .getDatabase(configuration.getDatabaseName())
                .getCollection(COMICBOOK_COLLECTION, Comicbook.class);
    }
}
```

### The repository tests

And we add a test class to test this list method.

```java
@MicronautTest
class ComicbookRepositoryTest {

    @Inject MongoClient mongoClient;

    @Inject ComicbookRepository repository;

    @Test void list() throws Exception {
        assertEquals(0, repository.list().blockingGet().size());
    }
}
```

### Small configuration

We add a small configuration to get the name of the database, with a default value `kickoff-2019`.

```java
@ConfigurationProperties("kickoff")
public class ComicbookConfiguration {

    private String databaseName = "kickoff-2019";

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
```

If you want to change the configuration add in your `application.yml` values for keys you want to defined.

```yaml
kickoff:
  databaseName: kickoff
```

### Exercice

Now you can complete the repository and repository test classes for other methods like:
* Create a comicbook
* Get a comicbook
* Update a comicbook
* Delete a comicbook

### A solution

Checkout the branch `step2-final` to have a solution.

```shell
$ git checkout step2-final
```

And checkout `step3-init` for the next step.

```shell
$ git checkout step3-init
```
