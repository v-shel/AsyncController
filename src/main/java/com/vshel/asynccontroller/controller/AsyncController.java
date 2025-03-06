package com.vshel.asynccontroller.controller;

import com.vshel.asynccontroller.service.CallableService;
import com.vshel.asynccontroller.service.DeferredService;
import com.vshel.asynccontroller.service.FutureService;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {

    private final FutureService futureService;
    private final DeferredService deferredService;
    private final CallableService callableService;

    @GetMapping("/deferred")
    public DeferredResult<ResponseEntity<String>> deferred(@RequestParam(required = false) Integer number) {
        log.info("Start method: deferred()");
        DeferredResult<ResponseEntity<String>> result = deferredService.getDeferred(number);
        log.info("End method: deferred()");
        return result;
    }

    @GetMapping("/callable")
    public Callable<ResponseEntity<String>> callable(@RequestParam(required = false) Integer number) {
        log.info("Start method: callable()");
        Callable<ResponseEntity<String>> response = callableService.getCallable(number);
        log.info("End method: callable()");
        return response;

    }

    @GetMapping("/future")
    public CompletableFuture<ResponseEntity<String>> future(@RequestParam(required = false) Integer number) {
        log.info("Start method: future()");
        CompletableFuture<ResponseEntity<String>> response = futureService.getFuture(number)
            .thenApply(ResponseEntity::ok);
        log.info("End method: future()");
        return response;
    }
}
