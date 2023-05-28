#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
	echo "Usage: sh 执行脚本.sh [base|services|stop|rm]"
	exit 1
}

build(){
  unzip website/back/vuejs.zip -d website/back/vuejs
  unzip website/front/nuxtjs.zip -d website/front/nuxtjs
  docker-compose build --no-cache
}

init(){
#  docker-compose build --no-cache
  yum remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-selinux docker-engine-selinux docker-engine docker-ce
  yum remove docker docker-client docker-client
  rm -rf /etc/docker
  rm -rf /run/docker
  rm -rf /var/lib/dockershim
  #删除docker的镜像文件
  rm -rf /var/lib/docker
  ps -ef|grep docker|grep -v grep|xargs kill -s
  #卸载docker相关包
  yum remove docker-*
  yum remove docker-ce-cli-*
  yum remove docker-scan-plugin*
  # 删除旧版docker-compose
  rm -rf /usr/local/bin/docker-compose
  rm -rf /usr/bin/docker-compose
  sudo rm /usr/local/bin/docker-compose

  # step 1: 安装必要的一些系统工具
  sudo yum install -y yum-utils device-mapper-persistent-data lvm2
  sudo yum install -y unzip zip
  # Step 2: 添加软件源信息
  sudo yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  # Step 3
  sudo sed -i 's+download.docker.com+mirrors.aliyun.com/docker-ce+' /etc/yum.repos.d/docker-ce.repo
  # Step 4: 更新并安装Docker-CE
  sudo yum makecache fast
  sudo yum -y install docker-ce
  # Step 4: 开启Docker服务
  sudo service docker start
  # Step 4: 开启Docker服务
  sudo service docker start
  sudo systemctl enable docker

  sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
  sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
  docker-compose --version

}

# 启动基础环境（必须）
base(){
	docker-compose up -d lita-mysql lita-redis lita-nacos lita-sentinel
}

# 启动程序模块（必须）
services(){
	docker-compose up -d lita-website-front lita-website-admin  api-gateway  service-acl service-cms  service-msm service-order service-oss service-sta service-ucenter service-vod service-edu
}

# 关闭所有环境/模块
stop(){
	docker-compose stop
}

# 删除所有环境/模块
rm(){
	docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"base")
	base
;;
"services")
	services
;;
"stop")
	stop
;;
"rm")
	rm
;;
"build")
  build
;;
"init")
  init
;;
*)
	usage
;;
esac

