# Use the official maven/Java 8 image to create a build-time images
FROM maven:3.6.0-jdk-8 as builder

# Set the current working directory inside the image
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY ./pom.xml ./pom.xml

# Build all the dependencies in preparation to go offline
RUN mvn dependency:go-offline -B

# Copy the project source
COPY ./src ./src

# Package the application
RUN mvn package -DskipTests

# Use OpenJDK to run the app
FROM openjdk:8-jre-alpine

# Set application's jar to app.jar
COPY --from=builder /app/target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
