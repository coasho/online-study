package com.atguigu.serviceorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.serviceorder.entity.Order;
import com.atguigu.serviceorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-05-12
 */
@RestController
@RequestMapping("/serviceorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("/createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, memberId);
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("/getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @GetMapping("/isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable("memberid") String memberid,
                               @PathVariable("id") String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().
                eq("member_id", memberid).
                eq("course_id", id).
                eq("status", 1));
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

