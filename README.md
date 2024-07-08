# About
```
Note! There are two products available for you to query. Theirs ids:
- 45603f96-1ef0-465c-9a7f-bb9e106e2b64
- 45603f96-1ef0-465c-9a7f-bb9e106e2b61
```

It's a modular monolith application build with Spring Boot 3.2.x, Kotlin 1.9.x and gradle multi-project approach

## Modules
There are 3 modules that one may want to start with:
- modules:deployment - Modular Monolith Deployment module. It's the module that glues all applications together into single deployment unit
- modules:apps:product - Module which contains `product-service`. All logic and infrastructure is placed in a single application module. Contains default Fixed in-memory HashMap based database
- modules:apps:discount - Module which contains `discount-service`. It's build with hexagonal architecture approach. Contains dependency-free domain and application modules. Entire framework (Spring Boot) related code was placed in dedicated module (`infrastructure`)

# How to's
## How to use
Rest API is being documented by two separate openapi specifications which are exposed on:
- `/discounts/_interface`
- `/products/_interface`

Please start there!

## How to run locally via docker
```
Note: Docker and docker-compose is needed to run via ./run-local.sh script. Docker-compose is used to provide simpler network exposure
```

To run locally, go to `codebase` and just run `./run-local.sh`

## How to build & test
```
prerequisite: JDK 21 is needed
```

To build and test, go to `codebase` and run `./gradlew build`

## How to only build
To build artifacts, go to `codebase` and run `./gradlew build -x test -x detekt`

## How to only test
### Run tests suite
To run tests suite, go to `codebase` andrun `./gradlew test -x detekt`

### Run static analysis
To run static analysis, go to `codebase` and run `./gradlew detekt`