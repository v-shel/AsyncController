package com.vshel.asynccontroller.controller;

import com.vshel.asynccontroller.service.AsyncService;
import com.vshel.asynccontroller.service.CallableService;
import com.vshel.asynccontroller.service.DeferredService;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {

    private final AsyncService asyncService;
    private final DeferredService deferredService;
    private final CallableService callableService;

    @GetMapping("/deferred")
    public DeferredResult<ResponseEntity<String>> getDeferredResult() {
        log.info("Start method: getDeferredResult()");
        DeferredResult<ResponseEntity<String>> result = deferredService.getDeferred();
        log.info("End method: getDeferredResult()");
        return result;
    }

    @GetMapping("/callable")
    public Callable<ResponseEntity<String>> getCallableResponse() {
        log.info("Start method: getCallableResponse()");
        Callable<ResponseEntity<String>> response = callableService.getCallable();
        log.info("End method: getCallableResponse()");
        return response;

    }

    @GetMapping("/future")
    public CompletableFuture<ResponseEntity<String>> getAsyncResponse() {
        log.info("Start method: getAsyncResponse()");
        CompletableFuture<ResponseEntity<String>> response = asyncService.getAsync()
            .thenApply(ResponseEntity::ok);
        log.info("End method: getAsyncResponse()");
        return response;
    }
}
