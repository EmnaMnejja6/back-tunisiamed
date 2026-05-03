# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim
# Copy jar into container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
# Expose port 8080
EXPOSE 8080
# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]
