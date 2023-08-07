FROM openjdk:17
COPY JAR_FILE=./*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]