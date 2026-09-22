# Algorithm Cost Lab

## The Question

How much does an algorithmic choice matter when the amount of data grows?

## The Problem

A backend service needs to check whether a user ID exists in a large
collection of users before processing a request. This project answers
one question with an actual experiment: does the lookup strategy you
pick matter once the dataset gets big?

Think of it like choosing how to find a name in a phone book. Flip
page by page (linear search), or open to the middle and narrow down
each time (binary search), or use an index that jumps straight to the
name (hash lookup). All three "work." They do not all take the same
time once the book has a million names.

## The Implementations

Three approaches to the same lookup, side by side:

| Approach      | Time Complexity        | space Complexity (Auxiliary)   |
|---------------|------------------------|--------------------------------|
| Linear Search | O(n)                   | O(1)                           |
| Binary Search | O(log n)               | O(1)                           |
| HashSet Lookup| O(1) average           | O(n)                           |

## The Theory

- **Linear search** checks IDs one by one. Doubling the dataset roughly
  doubles the worst-case work.
- **Binary search** needs a sorted collection. It halves the search
  space on each step, so doubling the dataset adds only one more step.
- **HashSet lookup** computes a hash and jumps to the right bucket.
  Lookup time stays roughly flat as the dataset grows, but it needs
  space proportional to the number of entries.

This is a real trade-off, not a free win: HashSet trades memory for
speed.

## The Experiment

Benchmarks run with JMH (Java Microbenchmark Harness), not
`System.nanoTime()` and not HTTP request timing. HTTP timing would mix
in Spring, networking, and JVM warm-up noise that has nothing to do
with the algorithm itself. JMH isolates the actual computation.

Spring Boot exposes the experiment through a REST endpoint. The
benchmark logic runs directly against the Java implementations,
independent of the web layer.

```
GET /api/benchmark?size=1000000
```

Dataset sizes tested: 10,000 / 100,000 / 1,000,000 (10,000,000 if the
machine handles it without memory issues).

## Architecture

```
Client
  │
  ▼
Spring Boot REST API
  │
  ▼
Benchmark Service
  │
  ▼
JMH Benchmark
  │
  ├── Linear Search   O(n)
  ├── Binary Search   O(log n)
  └── HashSet Lookup  O(1) avg
  │
  ▼
Benchmark Results
```

## Results

*Not yet run. This table will be filled in from actual JMH output once
the benchmarks execute — no numbers are invented ahead of time.*

| Dataset Size | Linear | Binary | HashSet |
|--------------|--------|--------|---------|
| 10K          | TBD    | TBD    | TBD     |
| 100K         | TBD    | TBD    | TBD     |
| 1M           | TBD    | TBD    | TBD     |

A chart (`results/performance.png`) will accompany the table.

## The Trade-offs

Faster is not automatically better. HashSet wins on time but costs
O(n) extra memory. Binary search is a solid middle ground on time but
requires the data to be sorted first, which has its own cost. Linear
search needs no setup at all, which matters for small or one-off
lookups.

## What I Learned

This project makes one narrow point: for repeated lookups on a large
dataset, algorithm choice measurably changes the work involved. It
does **not** claim that Big O predicts cloud cost. Constant factors,
JVM behavior, caching, I/O, and database access all affect real-world
cost too. Algorithmic efficiency is one layer to check before reaching
for infrastructure-level fixes, not a replacement for them.

## Repository Structure

```
algorithm-cost-lab/
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── README.md
├── SETUP.md
├── REQUIREMENTS.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/ataraxia/algorithmcostlab/
│   │   │   ├── AlgorithmCostLabApplication.java
│   │   │   ├── controller/
│   │   │   │   └── BenchmarkController.java
│   │   │   ├── service/
│   │   │   │   └── BenchmarkService.java
│   │   │   ├── algorithm/
│   │   │   │   ├── LinearSearch.java
│   │   │   │   ├── BinarySearch.java
│   │   │   │   └── HashLookup.java
│   │   │   ├── benchmark/
│   │   │   │   └── LookupBenchmark.java
│   │   │   └── model/
│   │   │       └── BenchmarkResult.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ataraxia/algorithmcostlab/
│           └── algorithm/
│               ├── LinearSearchTest.java
│               ├── BinarySearchTest.java
│               └── HashLookupTest.java
└── results/
    ├── benchmark.csv
    └── performance.png
```

## Scope

**In scope:** Java, Spring Boot, Maven, three lookup implementations,
dataset generator, JMH benchmarks, one REST endpoint, CSV output, one
chart.

**Out of scope (for now):** frontend, database, auth, Redis, Docker,
Kubernetes, cloud deployment, distributed benchmarking, microservices.
