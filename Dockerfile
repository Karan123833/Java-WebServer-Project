# --- Stage 1: Build the application ---
# Use an image that has both Maven and Java 17
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Run Maven to build the application. This will also download dependencies.
RUN mvn clean install

# --- Stage 2: Create the final, smaller image ---
# Use the lightweight JRE image for the final container
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy ONLY the final application JAR from the 'build' stage
COPY --from=build /app/target/MultiThreadingWebServer-1.0-SNAPSHOT.jar ./app.jar

# Expose the port the server runs on
EXPOSE 8010

# Command to run the application
CMD ["java", "-jar", "app.jar"]