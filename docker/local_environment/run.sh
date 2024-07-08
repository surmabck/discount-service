#!/bin/bash

JAR_FILE=$([ ".${JAR_FILE}" == "." ] && echo "deployment-0.0.1-SNAPSHOT.jar" || echo "${JAR_FILE}")
ENVIRONMENT="local"
APPLICATION_OPTS="-Dspring.profiles.active=${ENVIRONMENT} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5006 "

exec java \
    ${JAVA_OPTS} \
    ${APPLICATION_OPTS} \
    -jar "${JAR_FILE}"
