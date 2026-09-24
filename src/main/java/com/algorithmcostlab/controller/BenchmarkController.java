package com.algorithmcostlab.controller;

import com.algorithmcostlab.model.BenchmarkResponse;
import com.algorithmcostlab.service.BenchmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkController {

    private final BenchmarkService benchmarkService;

    public BenchmarkController(BenchmarkService benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @GetMapping("/api/benchmark")
    public BenchmarkResponse getBenchmark(@RequestParam int size) {
        return benchmarkService.getResultsForSize(size);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleUnknownSize(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleMissingResults(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}