package com.vshel.asynccontroller.service;

import java.util.SplittableRandom;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class FutureService {

    private final SplittableRandom random;

    @Async
    public CompletableFuture<String> getFuture(Integer number) {
        log.info("Start method: getAsync()");
        CompletableFuture<String> response = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(50, 2000)); // Simulates a random duration operation
                log.info("Long async operation executed.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Future request number is: " + number;
        });
        log.info("End method: getAsync()");
        return response;
    }
}
