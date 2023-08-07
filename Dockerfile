FROM openjdk:17
COPY ./build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
