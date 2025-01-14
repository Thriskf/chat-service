# syntax=docker/dockerfile:1

# Optimized Dockerfile with improved comments and structure.
# For reference on Dockerfile commands, visit https://docs.docker.com/go/dockerfile-reference/

################################################################################

# Stage 1: Dependency Resolution and Download
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copy Maven Wrapper and configuration files
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml pom.xml

# Download dependencies leveraging Docker's caching capabilities.
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -DskipTests

################################################################################

# Stage 2: Build Application
FROM deps as build

WORKDIR /build

# Copy the application source code, configuration, and application properties
COPY src ./src
COPY pom.xml ./pom.xml
COPY src/main/resources/prod-application.properties src/main/resources/application.properties

# Build the application JAR
ARG ARTIFACT_ID=chat-service
ARG VERSION=1.0.0-SNAPSHOT
ARG JARNAME=${ARTIFACT_ID}-${VERSION}-runner.jar
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean package -DskipTests -Dquarkus.package.type=uber-jar && \
    cp target/${JARNAME} target/app.jar

################################################################################

# Stage 3: Runtime Environment
FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user to run the application
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable JAR from the build stage
COPY --from=build /build/target/app.jar app.jar

# Expose the application port
EXPOSE 8050

# Define a health check for the container
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s \
    CMD curl -f http://localhost:8050/health || exit 1

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]