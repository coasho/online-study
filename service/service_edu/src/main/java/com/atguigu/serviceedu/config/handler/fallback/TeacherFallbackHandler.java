package com.atguigu.serviceedu.config.handler.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;

import java.util.ArrayList;
import java.util.List;

public class TeacherFallbackHandler {
    public static R listConditionBlock(long current, long limit, TeacherQuery teacherQuery, BlockException blockException) {
        List<EduTeacher> list = new ArrayList<EduTeacher>();
        list.add(new EduTeacher().setIntro("服务未发生异常、超时,但已熔断"));
        return R.ok().data("total", 1).data("rows", list);
    }
    public static R listConditionFallback(long current, long limit, TeacherQuery teacherQuery) {
        List<EduTeacher> list = new ArrayList<EduTeacher>();
        list.add(new EduTeacher().setIntro("服务发生异常、超时"));
        return R.ok().data("total", 1).data("rows", list);
    }
}
