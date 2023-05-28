package com.atguigu.serviceorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.CourseInfoForm;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.UcenterMember;
import com.atguigu.serviceorder.entity.Order;
import com.atguigu.serviceorder.mapper.OrderMapper;
import com.atguigu.serviceorder.service.EduClient;
import com.atguigu.serviceorder.service.OrderService;
import com.atguigu.serviceorder.service.UcenterClient;
import com.atguigu.serviceorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-05-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    public String saveOrder(String courseId, String memberId) {
        //远程调用课程服务，根据课程id获取课程信息
        R r = eduClient.getCourseInfoDto(courseId);
        System.out.println("---------------------");
        System.out.println(r.getData().get("courseInfo"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll((Map<? extends String, ?>) eduClient.getCourseInfoDto(courseId).getData().get("courseInfo"));
        CourseInfoForm courseDto = jsonObject.toJavaObject(CourseInfoForm.class);
        //远程调用用户服务，根据用户id获取用户信息
        jsonObject.putAll((Map<? extends String, ?>) ucenterClient.getInfo(memberId).getData().get("item"));
        UcenterMember ucenterMember = jsonObject.toJavaObject(UcenterMember.class);
        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
