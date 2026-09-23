package com.algorithmcostlab.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;

/**
 * Standalone entry point, not part of Spring Boot startup. Run via
 * Maven (see below). Writes results/benchmark.csv from real JMH output.
 */
public class BenchmarkRunner {

    public static void main(String[] args) throws RunnerException {
        new File("results").mkdirs();

        Options options = new OptionsBuilder()
                .include(LookupBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.CSV)
                .result("results/benchmark.csv")
                .build();

        new Runner(options).run();
    }
}