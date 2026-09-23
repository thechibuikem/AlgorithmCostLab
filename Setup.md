# Setup

Algorithm Cost Lab runs in Docker. You do not need Java or Maven
installed on your machine. Docker handles both inside the container.

## 1. Install Docker

- **Windows / macOS**: install Docker Desktop from
  https://www.docker.com/products/docker-desktop
- **Linux**: install Docker Engine and the Compose plugin using your
  distro's package manager, or follow
  https://docs.docker.com/engine/install/

Confirm it installed correctly:

```
docker --version
docker compose version
```

Both commands should print a version number.

## 2. Clone the repository

```
git clone <repo-url>
cd algorithm-cost-lab
```

## 3. Build and run

```
docker compose up --build
```

This builds the image, starts the container, and exposes the app on
port 8080. First build takes a few minutes while Maven downloads
dependencies. Later builds are faster.

## 4. Verify it's running

```
curl "http://localhost:8080/api/benchmark?size=10000"
```

You should get back a JSON response with benchmark results for the
three lookup algorithms.

## 5. Find the output

Benchmark CSV and chart files are written to the `results/` folder in
your project directory, not just inside the container. This is
because `docker-compose.yml` mounts `./results` from your machine
into the container, so files persist after the container stops.

## 6. Stop the container

```
docker compose down
```

## Troubleshooting

- **Port 8080 already in use**: stop whatever else is using it, or
  change the port mapping in `docker-compose.yml` (for example
  `"8081:8080"`).
- **Build fails on dependency download**: check your internet
  connection. Maven needs to reach Maven Central on first build.
- **Container exits immediately**: run `docker compose logs` to see
  the actual error from the JVM.