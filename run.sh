#!/bin/bash

# Make sure script is executable - chmod +x run.sh
# Exit immediately if any command fails

set -e

echo "=== Ataraxia says, enjoy the show! ==="

echo "=== Building project ==="
mvn clean package


JAR=target/algorithm-cost-lab-0.1.0-jar-with-dependencies.jar

echo "=== Step 1: Running BenchmarkRunner ==="
# docker run --rm \
#   -v "$(pwd)":/app \
#   -w /app \
#   maven:3.9-eclipse-temurin-21 \
#   mvn compile exec:java -Dexec.mainClass="com.algorithmcostlab.benchmark.BenchmarkRunner"

java -cp "$JAR" com.algorithmcostlab.benchmark.BenchmarkRunner




echo -e "\n=== Step 2: Running ChartGenerator ==="
# docker run --rm \
#   -v "$(pwd)":/app \
#   -w /app \
#   maven:3.9-eclipse-temurin-21 \
  mvn compile exec:java -Dexec.mainClass="com.algorithmcostlab.benchmark.ChartGenerator"

echo -e "\nAll tasks completed successfully!"