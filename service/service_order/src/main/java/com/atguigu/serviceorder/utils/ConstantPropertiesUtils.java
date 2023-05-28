package com.atguigu.serviceorder.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${pay.api.query}")
    private String apiUrlQuery;

    @Value("${pay.api.unifiedorder}")
    private String unifiedorder;
    @Value("${weixin.pay.appid}")
    private String appId;

    @Value("${pay.api.partner}")
    private String partner;

    @Value("${pay.api.partnerkey}")
    private String partnerKey;

    @Value("${pay.api.notifyurl}")
    private String notifyUrl;

    public static String API_URL_QUERY;

    public static String UNIFIED_ORDER;
    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;


    public void afterPropertiesSet() {
        API_URL_QUERY = apiUrlQuery;
        UNIFIED_ORDER=unifiedorder;
        APP_ID = appId;
        PARTNER = partner;
        PARTNER_KEY = partnerKey;
        NOTIFY_URL = notifyUrl;
    }
}
