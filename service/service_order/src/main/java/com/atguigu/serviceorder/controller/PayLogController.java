package com.atguigu.serviceorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-05-12
 */
@RestController
@RequestMapping("/serviceorder/pay-log")
public class PayLogController {
    @Autowired
    private PayLogService payService;
    /**
     * 生成二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}/{payType}")
    public R createNative(@PathVariable("orderNo") String orderNo,@PathVariable("payType") int payType) {
        Map map = payService.createNative(orderNo,payType);
        return R.ok().data(map);
    }
    @GetMapping("/queryPayStatus/{out_trade_no}")
    public R queryPayStatus(@PathVariable("out_trade_no") String out_trade_no) {
        //调用查询接口
        Map<String, String> map = payService.queryPayStatus(out_trade_no);
        if (map == null) {//出错
            return R.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

