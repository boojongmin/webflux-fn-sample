FROM parrotstream/ubuntu-java:latest
VOLUME /tmp
#ARG JAR_FILE
#ADD ${JAR_FILE} app.jar
ADD build/libs/userserver-0.1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
