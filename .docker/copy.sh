#!/bin/sh

# copy jar
echo "begin copy api_gateway "
cp ../infrastructure/api_gateway/target/api_gateway-0.0.1-SNAPSHOT.jar ./lita-back/api-gateway

echo "begin copy service_acl "
cp ../service/service_acl/target/service_acl-0.0.1-SNAPSHOT.jar ./lita-back/service-acl

echo "begin copy service_cms "
cp ../service/service_cms/target/service_cms-0.0.1-SNAPSHOT.jar ./lita-back/service-cms

echo "begin copy service_edu "
cp ../service/service_edu/target/service_edu-0.0.1-SNAPSHOT.jar ./lita-back/service-edu

echo "begin copy service_msm "
cp ../service/service_msm/target/service_msm-0.0.1-SNAPSHOT.jar ./lita-back/service-msm

echo "begin copy service_order "
cp ../service/service_order/target/service_order-0.0.1-SNAPSHOT.jar ./lita-back/service-order

echo "begin copy service_oss "
cp ../service/service_oss/target/service_oss-0.0.1-SNAPSHOT.jar ./lita-back/service-oss

echo "begin copy service_sta "
cp ../service/service_statistics/target/service_statistics-0.0.1-SNAPSHOT.jar ./lita-back/service-sta

echo "begin copy service_ucenter "
cp ../service/service_ucenter/target/service_ucenter-0.0.1-SNAPSHOT.jar ./lita-back/service-ucenter

echo "begin copy service_vod "
cp ../service/service_vod/target/service_vod-0.0.1-SNAPSHOT.jar ./lita-back/service-vod
