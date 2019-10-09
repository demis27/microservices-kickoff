# Micronaut version of the comicbook application.

## Step 2 : Pojo and Repository

In this step we initiate the Pojo and a an repository for this Pojo using MongoDB reactive. Both are in the common project to be use by all micro-services. If your are not aware with MongoDB reactive, skip this step and checkout the step three branch and go to the step 3. 

```shell
$ git checkout step3
```

We add a test class to test the repository with Flapdoodle for Mongo and add a configuration class to overide the name of the database.

* [The Pojo](#the-pojo)
* [The repository](#the-repository)
* [The repository test](#the-repository-test)
* [Small configuration](#small_configuration)

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

A minimal repository for comicbook. We use a reactive MongoDB API and implement a simple list method and other methods.

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

And we add a test class

```java
@MicronautTest
class ComicbookRepositoryTest {

    @Inject MongoClient mongoClient;

    @Inject ComicbookRepository repository;

    @AfterEach
    void removeAllData() throws Exception {
        repository.list().blockingGet().stream().forEach(comicbook -> repository.delete(comicbook.getId()));
    }

    @Test void list() throws Exception {
        assertEquals(0, repository.list().blockingGet().size());
        create("Amazing Spider-Man");
        assertEquals(1, repository.list().blockingGet().size());
    }

    [...]

    private Comicbook create(String name) {
        Comicbook comicbook = new Comicbook();
        comicbook.setName(name);
        return repository.create(comicbook).blockingGet();
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
