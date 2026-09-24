// package com.algorithmcostlab.service;

// import com.algorithmcostlab.model.BenchmarkResponse;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;

// import static org.junit.jupiter.api.Assertions.*;

// class BenchmarkServiceTest {

//     private Path tempCsv;

//     @Test
//     void returnsResultsForKnownSize() throws IOException {
//         tempCsv = Files.createTempFile("benchmark", ".json");
//         Files.writeString(tempCsv, String.join("\n",
//                 "\"Benchmark\",\"Mode\",\"Threads\",\"Samples\",\"Score\",\"Score Error (99.9%)\",\"Unit\",\"Param: size\"",
//                 "\"...LookupBenchmark.linearSearch\",\"avgt\",\"1\",\"5\",\"1200.5\",\"10.2\",\"ns/op\",\"10000\"",
//                 "\"...LookupBenchmark.binarySearch\",\"avgt\",\"1\",\"5\",\"45.1\",\"1.1\",\"ns/op\",\"10000\"",
//                 "\"...LookupBenchmark.hashLookup\",\"avgt\",\"1\",\"5\",\"12.3\",\"0.4\",\"ns/op\",\"10000\""
//         ));

//         BenchmarkService service = new BenchmarkService(tempCsv);
//         BenchmarkResponse response = service.getResultsForSize(10000);

//         assertEquals(10000, response.datasetSize());
//         assertEquals(3, response.results().size());
//         assertTrue(response.results().stream()
//                 .anyMatch(r -> r.algorithm().equals("Linear Search") && r.complexity().equals("O(n)")));
//     }

//     @Test
//     void throwsWhenSizeNotFound() throws IOException {
//         tempCsv = Files.createTempFile("benchmark", ".csv");
//         Files.writeString(tempCsv, String.join("\n",
//                 "\"Benchmark\",\"Mode\",\"Threads\",\"Samples\",\"Score\",\"Score Error (99.9%)\",\"Unit\",\"Param: size\"",
//                 "\"...LookupBenchmark.linearSearch\",\"avgt\",\"1\",\"5\",\"1200.5\",\"10.2\",\"ns/op\",\"10000\""
//         ));

//         BenchmarkService service = new BenchmarkService(tempCsv);
//         assertThrows(IllegalArgumentException.class, () -> service.getResultsForSize(999999));
//     }

//     @AfterEach
//     void cleanup() throws IOException {
//         if (tempCsv != null) Files.deleteIfExists(tempCsv);
//     }
// }