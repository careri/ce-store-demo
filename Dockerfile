# syntax=docker/dockerfile:1

# Copy files
FROM gradle:8.10.2-jdk21 AS files
WORKDIR /app
COPY buildSrc/ buildSrc/
COPY stores/ stores/
COPY gradle/libs.versions.toml gradle/libs.versions.toml
COPY gradlew gradlew
COPY settings.gradle settings.gradle
COPY docker-resources/gradle.properties gradle.properties

# Build
FROM files AS build
RUN gradle wrapper stores:build

# Run tests
FROM build AS test
WORKDIR /app
RUN gradle wrapper stores:test

# Build
FROM build AS publish
RUN gradle wrapper build

# Slim image with compiled jar only
FROM amazoncorretto:23.0.0-alpine3.20 AS app
COPY --from=publish /app/stores/build/resources/main /app/resources/
COPY --from=publish /app/stores/build/libs/stores-0.0.1-SNAPSHOT.jar /app/stores-0.0.1-SNAPSHOT.jar
WORKDIR /app
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
EXPOSE 3000
# ENTRYPOINT exec java $JAVA_OPTS -jar cestoredemo.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar stores-0.0.1-SNAPSHOT.jar
