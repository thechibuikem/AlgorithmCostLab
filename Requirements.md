# Requirements and Task Breakdown

## Functional Requirements

### MUST

| ID | Requirement | Feature area |
|----|-------------|---------------|
| FR1 | Implement linear search, binary search, and HashSet lookup for user ID checks | Algorithms |
| FR2 | Generate a dataset of realistic user IDs at 10K, 100K, and 1M sizes | Dataset |
| FR3 | Benchmark all three algorithms using JMH, not `System.nanoTime()` or HTTP timing | Benchmarking |
| FR4 | Expose a REST endpoint (`GET /api/benchmark?size=`) that returns structured JSON results | API |
| FR5 | Write benchmark results to a CSV file | Output |
| FR6 | Containerize the app with Docker so setup is a single path across OSes | Infrastructure |
| FR7 | README documents the real experiment and real results, no invented numbers | Docs |

### SHOULD

| ID | Requirement | Feature area |
|----|-------------|---------------|
| FR8 | Generate a chart (PNG) visualizing scaling behavior across dataset sizes | Output |
| FR9 | Test a 10M dataset if the machine handles it without memory issues | Dataset |
| FR10 | Pin container CPU/memory limits so benchmark numbers aren't skewed by container overhead | Infrastructure |

Explicitly out of scope: frontend, database, authentication, Redis, Kubernetes, cloud deployment, distributed benchmarking, microservices.

## Task Breakdown

Owner is David (Ataraxia) for all tasks unless stated otherwise. Steps are one task at a time, per the workflow.

### Phase 1 — Docs and infrastructure setup

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 1. README.md | none | Write project definition, architecture, plain-English explanation, structure | Reviewed and accurate against spec | `README.md` |
| 2. Docker setup | Task 1 | Write Dockerfile (multi-stage build), docker-compose.yml with resource limits and results volume mount | `docker compose up --build` succeeds against a stub `pom.xml` | `Dockerfile`, `docker-compose.yml` |
| 3. SETUP.md | Task 2 | Write install and run steps using the Docker path only | A person with no Java/Maven installed can follow it and hit the endpoint | `SETUP.md` |
| 4. This document | Tasks 1-3 | Requirements + task breakdown | Reviewed by David | `REQUIREMENTS.md` |

### Phase 2 — Scaffolding

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 5. Maven project init | Phase 1 | Create `pom.xml` with Spring Boot, JMH, JMH annotation processor dependencies | `mvn clean package` succeeds locally and in Docker | `pom.xml` |
| 6. Package structure | Task 5 | Create `controller/`, `service/`, `algorithm/`, `model/` packages | Structure matches README diagram | directory structure |
| 7. Dataset generator | Task 6 | Generate `user-000001` style IDs at configurable size | Produces correct count, no duplicates | `algorithm/DatasetGenerator.java` (or similar) |

### Phase 3 — Algorithms

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 8. LinearSearch | Task 7 | Sequential scan over a List | Unit test: finds existing ID, correctly reports missing ID | `algorithm/LinearSearch.java` |
| 9. BinarySearch | Task 7 | Binary search over a sorted collection | Unit test: same as above, plus confirms input must be sorted | `algorithm/BinarySearch.java` |
| 10. HashLookup | Task 7 | HashSet-based lookup | Unit test: same as above | `algorithm/HashLookup.java` |

### Phase 4 — Benchmarking

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 11. JMH harness | Phase 3 | Wire all three implementations into JMH benchmark classes | Benchmarks run via `mvn` and produce output | `*Benchmark.java` classes |
| 12. Run benchmarks | Task 11 | Execute across 10K/100K/1M (and 10M if feasible), multiple iterations | Raw JMH output captured | benchmark run logs |
| 13. CSV export | Task 12 | Write results to `results/benchmark.csv` | File contains real measured values, matches JMH output | `results/benchmark.csv` |
| 14. Chart | Task 13 | Generate `results/performance.png` from CSV | Chart visually shows the three growth curves | `results/performance.png` |

### Phase 5 — API layer

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 15. BenchmarkResult model | Phase 3 | Define fields: algorithm, complexity, executionTime | Matches JSON shape in README | `model/BenchmarkResult.java` |
| 16. BenchmarkService | Task 15, Phase 4 | Orchestrate running benchmarks and building results | Returns correct results for a given size | `service/BenchmarkService.java` |
| 17. BenchmarkController | Task 16 | Expose `GET /api/benchmark?size=` | Manual `curl` test returns expected JSON | `controller/BenchmarkController.java` |

### Phase 6 — Close-out

| Task | Depends on | Steps | Acceptance criteria | Files |
|------|-----------|-------|----------------------|-------|
| 18. Fill README results | Phase 4 | Paste real benchmark numbers and chart into README | No placeholder or invented values remain | `README.md` |
| 19. Final review | Task 18 | Check every claim in README against actual output; confirm no overclaiming on Big O vs. cost | Sign-off from David | `README.md` |
| 20. PR description | Task 19 | Summary, file-by-file breakdown, verification steps, reviewer notes | Matches actual confirmed tests | PR text |
