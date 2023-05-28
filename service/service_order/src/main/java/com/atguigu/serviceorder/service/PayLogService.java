package com.atguigu.serviceorder.service;

import com.atguigu.serviceorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-05-12
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo, int payType);

    Map<String, String> queryPayStatus(String out_trade_no);

    void updateOrderStatus(Map<String, String> map);
}
