FROM gradle:8.7.0-jdk21-alpine AS base

WORKDIR /app

ENV GRADLE_USER_HOME=/app/.gradle

FROM base AS development
# The -t (continuous) flag combined with Spring DevTools enables hot reload
ENTRYPOINT ["gradle", "bootRun", "--continuous", "-PskipDownload=true"]

FROM base AS builder
COPY --chown=gradle:gradle . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21.0.2_13-jre-alpine AS production
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
