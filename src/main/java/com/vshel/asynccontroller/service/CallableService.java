package com.vshel.asynccontroller.service;

import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallableService {

    private final SplittableRandom random;

    public Callable<ResponseEntity<String>> getCallable(Integer number) {
        log.info("Start method: getCallable()");
        Callable<ResponseEntity<String>> response = () -> {
            Thread.sleep(random.nextInt(50, 2000)); // Simulates a random duration operation
            log.info("Long callable operation executed.");
            return ResponseEntity.ok("Callable request number is: " + number);
        };
        log.info("End method: getCallable()");
        return response;
    }
}
