package com.vshel.asynccontroller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AsyncControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void futureShouldReturnMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/async/future",
            String.class)).contains("Future request number is: null");
    }

    @Test
    void deferredShouldReturnMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/async/deferred",
            String.class)).contains("Deferred request number is: null");
    }

    @Test
    void callableShouldReturnMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/async/callable",
            String.class)).contains("Callable request number is: null");
    }

    @Test
    void futureShouldReturnMessageWithNumber() {
        String url = "http://localhost:" + port + "/async/future?number=" + port;
        assertThat(this.restTemplate.getForObject(url, String.class)).contains("Future request number is: " + port);
    }

    @Test
    void deferredShouldReturnMessageWithNumber() {
        String url = "http://localhost:" + port + "/async/deferred?number=" + port;
        assertThat(this.restTemplate.getForObject(url, String.class)).contains("Deferred request number is: " + port);
    }

    @Test
    void callableShouldReturnMessageWithNumber() {
        String url = "http://localhost:" + port + "/async/callable?number=" + port;
        assertThat(this.restTemplate.getForObject(url, String.class)).contains("Callable request number is: " + port);
    }
}
