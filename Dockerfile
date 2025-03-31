# Use a multi-stage build to optimize image size
# First stage: Build the application
FROM eclipse-temurin:17 AS build

WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Second stage: Create a lightweight runtime image
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
