# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Make port 8080 available to the world outside this container
EXPOSE 8080
EXPOSE 80

# Add the application's jar to the container
COPY ./target/redis-memcache-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "-Xmx512m" ,"/app.jar"]
