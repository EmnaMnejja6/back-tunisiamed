# Use official OpenJDK 17 image
FROM eclipse-temurin:17-jdk
# Copy jar into container
COPY target/projet-0.0.1-SNAPSHOT.jar app.jar
# Expose port 8080
EXPOSE 8080
# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]
