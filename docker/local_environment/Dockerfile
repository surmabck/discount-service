FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

EXPOSE 8080

ARG GIT_COMMIT
LABEL git-commit="${GIT_COMMIT}"
ENV GIT_COMMIT ${GIT_COMMIT}

ARG ENVIRONMENT

ENV JAR_FILE="deployment-0.0.1-SNAPSHOT.jar"
ENV JAVA_OPTS=" \
    -XX:+ExitOnOutOfMemoryError \
    -XX:+UseContainerSupport \
    -Dfile.encoding=UTF-8 \
"
RUN echo -e " \
    JAR_FILE=${JAR_FILE} \n \
    JAVA_OPTS=${JAVA_OPTS} \n \
"

ADD "${JAR_FILE}" "/"
ADD "run.sh" "/"

RUN chmod +x "/run.sh"

ENTRYPOINT ["/run.sh"]
