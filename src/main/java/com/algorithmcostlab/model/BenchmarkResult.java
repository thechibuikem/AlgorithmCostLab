package com.algorithmcostlab.model;

public record BenchmarkResult(
        String algorithm,
        String complexity,
        String executionTime
) {}