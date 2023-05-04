FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/domain.jar
COPY ${JAR_FILE} domain.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/domain.jar"]
