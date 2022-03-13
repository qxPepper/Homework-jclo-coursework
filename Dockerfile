FROM adoptopenjdk/openjdk11:jre-11.0.13_8-alpine

EXPOSE 8080

COPY target/Homework-jclo-coursework-0.0.1-SNAPSHOT.jar my_back.jar

CMD ["java", "-jar", "/my_back.jar"]