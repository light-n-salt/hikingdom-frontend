FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/batch.jar
COPY ${JAR_FILE} batch.jar

ARG SERVER_MODE
RUN echo "$SERVER_MODE"
ENV SERVER_MODE=$SERVER_MODE

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${SERVER_MODE}","-Duser.timezone=Asia/Seoul", "-jar", "/batch.jar"]
