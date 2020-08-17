FROM openjdk:12-alpine

COPY target/file-storage-0.0.1-SNAPSHOT.jar /file-repository.jar

CMD ["java", "-jar", "/file-repository.jar"]
