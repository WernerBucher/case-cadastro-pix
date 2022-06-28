FROM openjdk:8-jdk-slim
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS="-Xmx128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]