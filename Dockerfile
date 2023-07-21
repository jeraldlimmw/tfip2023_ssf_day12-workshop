# PART 1: Creating a container
# create an image based off this image
FROM maven:3.8-openjdk-18-slim AS builder

# create a directory and cd into the directory
WORKDIR /src

# everything after WORKDIR is now in /src
# copy pom.xml as pom.xml (. means no name change)
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

# copy directories (cannot use ., must give it a directory name)
COPY src src

# compile the Java program
RUN mvn package -Dmaven.test.skip=true

# SERVER_PORT=3000 java -jar target/day12-workshop-0.0.1-SNAPSHOT.jar

# PART 2: Creating a second container in first container
# run the application from the container
FROM openjdk:18-slim

WORKDIR /src

# copy the compiled JAR file to this new container
COPY --from=builder /src/target/day12-workshop-0.0.1-SNAPSHOT.jar .


ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}
# for single layer build (Part 1 only):
# ENTRYPOINT java -jar target/day12-workshop-0.0.1-SNAPSHOT.jar
# for multi layer build (w Part 2):
ENTRYPOINT java -jar day12-workshop-0.0.1-SNAPSHOT.jar

# in terminal -> docker build -t {account_name}/{image_name}:{version_number OR hash_signature} .

# docker run -d -p {port you want}:{port it is on} {account_name}/{image_name}:{version_number OR hash_signature}
# docker run -d -p {port you want}:{port it is on} --name {given appname} {account_name}/{image_name}:{version_number OR hash_signature}

# commands:
# to check running containers' status -> docker ps
# to stop running -> docker stop {given appname OR container_id (or part of it)}
# to check all containers -> docker ps -a
# to run again -> docker start {given appname OR container_id}
# to remove the container -> docker rm {given appname OR container_id}
# to push to online repo -> docker push {account_name}/{image_name}:{version_number OR hash_signature}
# to create network -> docker create network {name}
# to remove image -> docker rmi {account_name}/{image_name}:{version_number OR hash_signature}