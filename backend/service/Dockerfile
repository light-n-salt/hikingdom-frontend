FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/service.jar
COPY ${JAR_FILE} service.jar

ARG SERVER_MODE
RUN echo "$SERVER_MODE"
ENV SERVER_MODE=$SERVER_MODE

ENTRYPOINT ["java", "-Dspring.profiles.active=${SERVER_MODE}","-Duser.timezone=Asia/Seoul", "-jar", "/service.jar"]
