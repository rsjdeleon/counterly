.PHONY: all build package docker-build-inventory docker-build-order docker-build-product docker-build-all clean

MVNW=./mvnw
MVN_FLAGS=-DskipTests
DOCKER ?= docker

all: build

build:
	$(MVNW) $(MVN_FLAGS) package

package: build

docker-build-inventory:
	$(DOCKER) build -f inventory/src/main/docker/Dockerfile -t counterly/inventory:local ./inventory

docker-build-order:
	$(DOCKER) build -f order/src/main/docker/Dockerfile -t counterly/order:local ./order

docker-build-product:
	$(DOCKER) build -f product/src/main/docker/Dockerfile -t counterly/product:local ./product

docker-build-all: docker-build-inventory docker-build-order docker-build-product

clean:
	$(MVNW) clean

# Convenience target to remove local module target directories (keeps source files)
purge-targets:
	@echo "Removing all local 'target' directories (use with care)"
	@find . -name target -type d -prune -exec rm -rf {} +

