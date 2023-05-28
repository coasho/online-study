FROM mysql:8
MAINTAINER coasho
ENV MYSQL_ROOT_PASSWORD hhbcflq
EXPOSE 3306
COPY ./conf/my.cnf /etc/mysql/conf.d
COPY ./sql/*.sql /docker-entrypoint-initdb.d/
RUN chmod 777 /docker-entrypoint-initdb.d/*.sql

