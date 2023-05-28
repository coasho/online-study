# 基础镜像
FROM redis
# author
MAINTAINER coasho

# 挂载目录
VOLUME /home/lita/redis
# 创建目录
RUN mkdir -p /home/lita/redis
# 指定路径
WORKDIR /home/lita/redis
# 复制conf文件到路径
COPY ./conf/redis.conf /home/lita/redis/redis.conf
