# syntax=docker/dockerfile:1

# ---- build stage: compile the Spring Boot jar with the Maven wrapper (JDK 25) ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /build

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src src

# A BuildKit cache mount keeps the Maven repo (and the wrapper's distribution) across builds, so deps
# aren't re-downloaded each time. Tests need Docker/Testcontainers, so they run in CI, not here.
RUN chmod +x mvnw && ./mvnw -B -DskipTests clean package && cp target/*.jar app.jar

# ---- runtime stage: slim JRE, non-root ----
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app
RUN useradd --system --uid 1001 --create-home appuser
COPY --from=build /build/app.jar app.jar
USER appuser

# Dev profile listens on 8585 (see application-dev.yaml); override via AUTOFINANCE_SERVER_PORT.
EXPOSE 8585
ENTRYPOINT ["java", "-jar", "app.jar"]
