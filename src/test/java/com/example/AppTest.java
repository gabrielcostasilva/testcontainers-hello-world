package com.example;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import kong.unirest.GenericType;
import kong.unirest.Unirest;

@Testcontainers
public class AppTest {

    @Container
    public GenericContainer api = 
        new GenericContainer<>("tvngoan/mock-posts-api:latest")
            .withExposedPorts(3000);

    @Test
    public void shouldAnswerWithTrue() {
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
