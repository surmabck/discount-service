#!/bin/bash

./codebase/gradlew clean build -p codebase --info --stacktrace -Dorg.gradle.jvmargs="-Xmx768m" -x test -x detekt --parallel
cp ./codebase/modules/deployment/build/libs/deployment-0.0.1-SNAPSHOT.jar  docker/local_environment/

docker run docker/local_environment --build-arg GIT_COMMIT=$(git rev-parse --short HEAD 2>&1)
docker-compose -f docker/local_environment/docker-compose.yml build \
    --parallel \
    --build-arg GIT_COMMIT=$(git rev-parse --short HEAD 2>&1) discount-service
docker-compose -f docker/local_environment/docker-compose.yml up --force-recreate
