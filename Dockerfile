# Build stage (Gradle image includes Gradle + JDK)
FROM gradle:8-jdk17-alpine AS builder
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
COPY src src

RUN gradle installDist --no-daemon -x test

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/install/minijava-server ./

EXPOSE 8080
ENTRYPOINT ["./bin/minijava-server"]
