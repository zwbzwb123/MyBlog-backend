FROM java:8

COPY *.jar /zwblog.jar

CMD ["--server.port=8081"]

EXPOSE 8081

ENTRYPOINT ["java","-jar","/zwblog.jar"]
