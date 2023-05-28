# 基础镜像
FROM nginx
# author
MAINTAINER coasho

# 创建目录
RUN mkdir -p /home/lita-admin
# 指定路径
WORKDIR /home
# 复制conf文件到路径
COPY ./conf/nginx.conf /etc/nginx/nginx.conf
# 复制html文件到路径
COPY ./vuejs /home/lita-admin
