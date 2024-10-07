# syntax=docker/dockerfile:1

# Copy buildSrc and build it, this doesn't change often
FROM gradle:8.10.2-jdk21 AS buildsrc
WORKDIR /app
COPY buildSrc/ buildSrc/
COPY gradle/libs.versions.toml gradle/libs.versions.toml
COPY settings.gradle settings.gradle
RUN gradle buildSrc:build

# Copy and build stores source files, changes most of the time
FROM buildsrc AS build
COPY stores/ stores/
RUN gradle stores:compileJava 

# Run tests
FROM build AS test
RUN gradle stores:test

# Publish
FROM test AS publish
RUN gradle build

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
