#!/bin/sh

# remove jar
echo "begin remove api_gateway "
rm  -r  ./lita-back/api-gateway/api_gateway-0.0.1-SNAPSHOT.jar

echo "begin remove service_acl "
rm  -r  ./lita-back/service-acl/service_acl-0.0.1-SNAPSHOT.jar

echo "begin remove service_cms "
rm  -r  ./lita-back/service-cms/service_cms-0.0.1-SNAPSHOT.jar

echo "begin remove service_edu "
rm  -r  ./lita-back/service-edu/service_edu-0.0.1-SNAPSHOT.jar

echo "begin remove service_msm "
rm  -r  ./lita-back/service-msm/service_msm-0.0.1-SNAPSHOT.jar

echo "begin remove service_order "
rm  -r  ./lita-back/service-order/service_order-0.0.1-SNAPSHOT.jar

echo "begin remove service_oss "
rm  -r  ./lita-back/service-oss/service_oss-0.0.1-SNAPSHOT.jar

echo "begin remove service_sta "
rm  -r  ./lita-back/service-sta/service_statistics-0.0.1-SNAPSHOT.jar

echo "begin remove service_ucenter "
rm  -r  ./lita-back/service-ucenter/service_ucenter-0.0.1-SNAPSHOT.jar

echo "begin remove service_vod "
rm  -r  ./lita-back/service-vod/service_vod-0.0.1-SNAPSHOT.jar
