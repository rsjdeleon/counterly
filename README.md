counterly - microservices mono-repo

Overview

This repository contains a multi-module Java (Spring) microservices example with the following modules:
- bom - bill-of-materials / wrapper (contains a Maven wrapper)
- common - shared library code
- inventory - inventory microservice
- order - order microservice
- product - product microservice

Other files:
- `docker-compose.yml` - local compose for services
- `k8s/` - Kubernetes deployment manifests for some services
- `Jenkinsfile` - CI pipeline

Quick checks and status
- The repository uses Maven and targets Java 21 (see root `pom.xml`).
- Each service has a `Dockerfile` under `src/main/docker/` and a `scripts/mysql-init.sql` script.
- There are committed build artifacts under `*/target/` — these should be removed and added to `.gitignore` (done).

Build (recommended)

1) With system Maven installed (recommended):

```bash
mvn -DskipTests package
```

2) Using the Maven wrapper that lives in `bom/` (less convenient):

```bash
# from repository root
./bom/mvnw -f pom.xml -DskipTests package
```

If you prefer to use a wrapper at the repo root, copy `bom/mvnw` and `bom/mvnw.cmd` to the repo root and commit them.

Docker image build examples

```bash
# Build inventory image
docker build -f inventory/src/main/docker/Dockerfile -t counterly/inventory:local ./inventory

# Build order image
docker build -f order/src/main/docker/Dockerfile -t counterly/order:local ./order
```

Recommended next changes (prioritized)

1) Remove committed `target/` artifacts and add/update `.gitignore` (done).
2) Move or add the Maven wrapper (`mvnw`) to the repository root for a consistent developer experience.
3) Centralize dependency and plugin management: consider using `bom/pom.xml` as a parent or add a top-level `dependencyManagement` in root `pom.xml` so submodules align versions.
4) Add a root `README.md` (this file) and short `CONTRIBUTING.md` describing build/test/run flow.
5) CI: ensure `Jenkinsfile` builds the multi-module project, builds Docker images (point to module Dockerfiles), and pushes images to a registry.
6) Kubernetes: add manifests for missing services (e.g., `product`) or add a Helm chart for deployment.
7) Security: verify there are no plaintext secrets in `application.properties` or SQL scripts.
8) Tests and code quality: add CI test stage, coverage reports, and (optionally) static analysis.

If you'd like, I can:
- Remove the checked-in `target/` artifacts (I will not delete them from git history but will remove them from the working tree and add `.gitignore`).
- Copy the Maven wrapper to repo root and validate builds with it.
- Add a simple `Makefile` or scripts to build and dockerize all modules.


Contact

Tell me which of the recommended tasks you want automated next and I'll apply them and run validation steps.  

