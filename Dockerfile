FROM openjdk:8

# Update apt
RUN apt-get update

# Install maven
RUN apt-get install -y maven

# Set correct java version
RUN update-java-alternatives -s java-1.8.0-openjdk-amd64

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "package"]

EXPOSE 27017
EXPOSE 8080
ENTRYPOINT java -jar target/sara-1.0-SNAPSHOT.jar