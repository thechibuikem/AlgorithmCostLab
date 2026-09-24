package com.algorithmcostlab.benchmark;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses results/benchmark.json, written by BenchmarkRunner via JMH's
 * JSON result format. JSON is used instead of CSV because JMH's CSV
 * writer does not include @Param columns; JSON does.
 */
public class BenchmarkResultReader {

    public record Row(String algorithm, int size, double scoreNanos) {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<Row> readAll(Path jsonPath) throws IOException {
        if (!Files.exists(jsonPath)) {
            throw new IllegalStateException(
                "results/benchmark.json not found. Run BenchmarkRunner first.");
        }

        JsonNode root = MAPPER.readTree(jsonPath.toFile());
        List<Row> rows = new ArrayList<>();

        for (JsonNode entry : root) {
            String fullName = entry.get("benchmark").asText();
            String algorithm = fullName.substring(fullName.lastIndexOf('.') + 1);
            int size = Integer.parseInt(entry.get("params").get("size").asText());
            double score = entry.get("primaryMetric").get("score").asDouble();
            rows.add(new Row(algorithm, size, score));
        }

        if (rows.isEmpty()) {
            throw new IllegalStateException(
                "results/benchmark.json has no entries. The benchmark run did not produce results.");
        }

        return rows;
    }
}