# TESTCONTAINERS - HELLO WORLD
This getting started with Testcontainers shows dependencies, annotations and overall use of Testcontainers. [_Testcontainers_](https://testcontainers.com) is a framework that uses Docker containers to run tests against dependencies, such as databases.

The main benefit is that you no longer rely on _Mocks_, but real implementations.

## Overview
This project consist of a single POJO ([`Post`](./src/main/java/com/example/Post.java)) and a test class ([AppTest](./src/test/java/com/example/AppTest.java)). 

The test class checks whether a containerised API returns the expected result.

## Testcontainers Anatomy
The code below explains the Testcontainer anatomy with our test class.

```java
@Testcontainers // (1)
public class AppTest {

    @Container // (2)
    public GenericContainer api = 
        new GenericContainer<>("tvngoan/mock-posts-api:latest")
            .withExposedPorts(3000); // (3)

    @Test
    public void shouldAnswerWithTrue() { // (4)
        String address = api.getHost();
        Integer port = api.getFirstMappedPort();

        Unirest.config().defaultBaseUrl("http://" + address+ ":" + port);

        var result = Unirest
                        .get("/posts")
                        .asObject(new GenericType<List<Post>>(){})
                        .getBody();

        assertEquals(100, result.size());
    }

}
```

1. Identifies this test as a Testcontainers
2. Links JUnit and the field
3. Sets a particular container image and port
4. Runs the test by submitting a POST request to the address and port set by the container. The should pass.

## Dependencies
Testcontainers require two dependencies: `org.testcontainers.junit-jupiter` and `org.testcontainers.testcontainers`. However, this project also relies on `org.projectlombok.lombok` for creating gets/sets for the POJO, and `com.konghq.unirest-java` - which is a REST client.