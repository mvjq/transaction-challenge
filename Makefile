# Default target
.DEFAULT_GOAL := help

.PHONY: help build-notest build run test clean bootRun stop

## Help
help: ## Show this help message
	@echo "Makefile commands"
	@echo ""
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "%-20s %s\n", $$1, $$2}'

## Build
build-notest: ## Build the application
	@echo "building Transaction application"
	$(GRADLE) build -x test


build:
	@echo "Building Transaction application"
	$(GRADLE) build

run:
	@echo "Running Transaction application"
	$(GRADLE) bootRun

stop:
	@echo "Running Transaction application"
	$(GRADLE) --stop
	@pkill -f "gradle" || true