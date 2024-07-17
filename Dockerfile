# Use an official openjdk image as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/URL_Shortener-0.0.1-SNAPSHOT.jar url-shortener-service.jar

# Make port 9000 available to the world outside this container
EXPOSE 9000

# Run the jar file
ENTRYPOINT ["java", "-jar", "url-shortener-service.jar"]

