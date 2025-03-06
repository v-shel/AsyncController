package com.vshel.asynccontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"/future", "/deferred", "/callable"})
    void futureShouldReturnValueAsync(String methodMapping) {
        String url = "http://localhost:" + port + "/async" + methodMapping;
        Queue<String> results = new ConcurrentLinkedQueue<>();

        makeApiCallThenAddResult(url, 1, results);
        makeApiCallThenAddResult(url, 2, results);
        makeApiCallThenAddResult(url, 3, results);
        makeApiCallThenAddResult(url, 4, results);
        makeApiCallThenAddResult(url, 5, results);
        makeApiCallThenAddResult(url, 6, results);
        makeApiCallThenAddResult(url, 7, results);
        makeApiCallThenAddResult(url, 8, results);
        makeApiCallThenAddResult(url, 9, results);
        makeApiCallThenAddResult(url, 10, results);

        await().atMost(30, TimeUnit.SECONDS).until(() -> results.size() == 10);
        assertThat(results).hasSize(10);

        AtomicInteger index = new AtomicInteger(0);
        assertThat(results).anyMatch(res -> !res.contains(String.valueOf(index.getAndIncrement())));
    }

    private void makeApiCallThenAddResult(String url, int callNum, Queue<String> results) {
        new CompletableFuture<String>()
            .completeAsync(() -> this.restTemplate.getForObject(url + "?number=" + callNum, String.class))
            .whenComplete((res, ex) -> results.add(res));
    }
}
