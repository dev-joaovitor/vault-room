FROM gradle:9.4.1-jdk21-alpine AS base

WORKDIR /app

ENV GRADLE_USER_HOME=/app/.gradle

FROM base AS development

ENTRYPOINT ["gradle", "bootRun", "-PskipDownload=true"]

FROM base AS builder
COPY --chown=gradle:gradle . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21.0.2_13-jre-alpine AS production
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
