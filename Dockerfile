# Use Eclipse Temurin JDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set work directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/ea_backend.jar ea_backend.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "ea_backend.jar"]
