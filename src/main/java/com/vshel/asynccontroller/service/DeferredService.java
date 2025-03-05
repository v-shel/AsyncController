package com.vshel.asynccontroller.service;

import java.util.SplittableRandom;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeferredService {

    private final SplittableRandom random;

    public DeferredResult<ResponseEntity<String>> getDeferred() {
        log.info("Start method: getDeferred()");
        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>(5000L);

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(random.nextInt(50, 2000)); // Simulate a long operation
                log.info("Long deferred operation executed.");
                deferredResult.setResult(ResponseEntity.ok("Deferred Response!"));
            } catch (Exception ex) {
                deferredResult.setErrorResult(ResponseEntity.status(500).body(ex.getMessage()));
                if (!Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        log.info("End method: getDeferred()");
        return deferredResult;
    }
}
