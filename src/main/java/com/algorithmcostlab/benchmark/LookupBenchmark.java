package com.algorithmcostlab.benchmark;

import com.algorithmcostlab.algorithm.BinarySearch;
import com.algorithmcostlab.algorithm.HashLookup;
import com.algorithmcostlab.algorithm.LinearSearch;
import com.algorithmcostlab.data.DataSetGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Level;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)

public class LookupBenchmark {

    @Param({"10000", "100000", "1000000"})
    public int size;

    private List<String> sortedIds;
    private Set<String> hashIndex;
    private String target;

    @Setup(Level.Trial)
    public void setup() {
        sortedIds = DataSetGenerator.generate(size);
        hashIndex = HashLookup.buildIndex(sortedIds);
        // Fixed, existing target near the middle. Not a worst case for
        // any one algorithm, so the comparison stays fair.
        target = sortedIds.get(size / 2);
    }
    
    @Benchmark
    public boolean linearSearch() {
        return LinearSearch.exists(sortedIds, target);
    }

    @Benchmark
    public boolean binarySearch() {
        return BinarySearch.exists(sortedIds, target);
    }

    @Benchmark
    public boolean hashLookup() {
        return HashLookup.exists(hashIndex, target);
    }
}