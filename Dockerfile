
FROM java:8
VOLUME /tmp
COPY target/spring-boot-api-project-seed-1.0.jar demo.jar
RUN bash -c "touch /demo.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
