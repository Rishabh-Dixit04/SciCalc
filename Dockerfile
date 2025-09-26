# Stage 1: The Build Stage (Uses a full JDK to compile the code and build the JAR)
# We use a base image with Java Development Kit (JDK) and Maven pre-installed
#FROM maven:3.9.6-openjdk-21-slim AS build

FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
# Copying pom first helps Docker cache the dependencies if only the code changes
COPY pom.xml .
COPY src ./src

# Build the application (This executes 'mvn clean install' just like in Jenkins)
# The result is a JAR file in the target/ directory
RUN mvn clean install -DskipTests

# Find the name of your compiled JAR file.
# It usually follows the pattern: <artifactId>-<version>.jar
# Assuming your pom.xml artifactId is 'scientific-calculator' and version is '1.0-SNAPSHOT'
# Replace the artifact name if yours is different!
ENV ARTIFACT_NAME="scientific-calculator-executable.jar"

# Stage 2: The Runtime Stage (Uses a minimal JRE to run the application)
# This uses a much smaller image that only contains the Java Runtime Environment (JRE)
#FROM openjdk:21-slim-buster
FROM eclipse-temurin:21-jre-alpine

# Set the working directory where the application will run
WORKDIR /usr/app

## Copy the generated JAR file from the 'build' stage to this runtime stage
#COPY --from=build /app/target/${ARTIFACT_NAME} app.jar

# CRITICAL FIX: Copy the specific EXECUTABLE JAR (renamed by Shade Plugin)
# and give it the generic name 'app.jar' in the final image.
COPY --from=build /app/target/scientific-calculator-executable.jar app.jar

# Define the command to run the application when the container starts.
# This explicitly tells Java to run your specific Main class.
#ENTRYPOINT ["java", "-cp", "app.jar", "org.example.Main"]

ENTRYPOINT ["java", "-jar", "app.jar"]