package com.algorithmcostlab.model;

import java.util.List;

public record BenchmarkResponse(
        int datasetSize,
        List<BenchmarkResult> results
) {}