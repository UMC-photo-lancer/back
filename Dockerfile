FROM openjdk:17
#ARG JAR_FILE=build/libs/*.jar
COPY ./build/libs/photolancer-0.0.1-DNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]