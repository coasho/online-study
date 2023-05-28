FROM  openjdk:8-jre

RUN mkdir /usr/local/dockor/ && mkdir /usr/local/dockor/Sentinel

COPY  ./sentinel-dashboard-1.8.6.jar /usr/local/dockor/Sentinel

WORKDIR  /usr/local/dockor/Sentinel

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Xmx32m","-Xms32m","sentinel-dashboard-1.8.6.jar"]
