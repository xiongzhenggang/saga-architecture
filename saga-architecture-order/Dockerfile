#FROM openjdk:12-alpine
#ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
#    JAVA_OPTS="" \
#    PORT=80 \
#    PROFILES="default"
#ADD /target/*.jar /app.jar
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar --spring.config.location=/config/application.yaml --spring.profiles.active=$PROFILES"]
#EXPOSE $PORT
FROM java:21
VOLUME /tmp
ADD ./target/saga-architecture-order.jar /app.jar
RUN bash -c 'touch /app.jar'
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime && echo Asia/Shanghai > /etc/timezone
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","/app.jar"]