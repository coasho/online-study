# 基础镜像
FROM  openjdk:8-jre-alpine
# author
MAINTAINER coasho

# 挂载目录
VOLUME /home/lita
# 创建目录
RUN mkdir -p /home/lita
# 指定路径
WORKDIR /home/lita
# 复制jar文件到路径
COPY ./service_ucenter-0.0.1-SNAPSHOT.jar /home/lita/service_ucenter-0.0.1-SNAPSHOT.jar
# 启动文章服务
ENTRYPOINT ["nohup","java","-Xmx50m","-Xms50m","-jar","service_ucenter-0.0.1-SNAPSHOT.jar"]
