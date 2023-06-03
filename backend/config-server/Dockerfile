FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/config-server.jar
COPY ${JAR_FILE} config-server.jar

ARG SERVER_MODE
RUN echo "$SERVER_MODE"
ENV SERVER_MODE=$SERVER_MODE

EXPOSE 8888

ENTRYPOINT ["java", "-Dspring.profiles.active=${SERVER_MODE}","-Duser.timezone=Asia/Seoul", "-jar", "/config-server.jar"]
