package com.algorithmcostlab.service;

import com.algorithmcostlab.benchmark.BenchmarkResultReader;
import com.algorithmcostlab.model.BenchmarkResponse;
import com.algorithmcostlab.model.BenchmarkResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class BenchmarkService {

    private static final Map<String, String> COMPLEXITY_BY_ALGORITHM = Map.of(
            "linearSearch", "O(n)",
            "binarySearch", "O(log n)",
            "hashLookup", "O(1) average"
    );

    private static final Map<String, String> DISPLAY_NAME_BY_ALGORITHM = Map.of(
            "linearSearch", "Linear Search",
            "binarySearch", "Binary Search",
            "hashLookup", "HashSet Lookup"
    );

    private final Path jsonPath;

    public BenchmarkService() {
        this(Path.of("results/benchmark.json"));
    }

    // Package-private overload so tests can point at a fixture file
    // instead of needing a real JMH run.
    BenchmarkService(Path jsonPath) {
        this.jsonPath = jsonPath;
    }

    public BenchmarkResponse getResultsForSize(int size) {
        List<BenchmarkResultReader.Row> rows;
        try {
            rows = BenchmarkResultReader.readAll(jsonPath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read benchmark results", e);
        }

        List<BenchmarkResult> results = rows.stream()
                .filter(row -> row.size() == size)
                .map(row -> new BenchmarkResult(
                        DISPLAY_NAME_BY_ALGORITHM.getOrDefault(row.algorithm(), row.algorithm()),
                        COMPLEXITY_BY_ALGORITHM.getOrDefault(row.algorithm(), "unknown"),
                        String.format("%.2f ns/op", row.scoreNanos())
                ))
                .toList();

        if (results.isEmpty()) {
            throw new IllegalArgumentException(
                    "No benchmark results for size " + size +
                    ". Available sizes come from BenchmarkRunner's @Param values.");
        }

        return new BenchmarkResponse(size, results);
    }
}