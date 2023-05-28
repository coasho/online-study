package com.atguigu.serviceedu.controller.admin;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.GuliException;
import com.atguigu.serviceedu.config.handler.fallback.TeacherFallbackHandler;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-14
 */
@Api("老师数据操作")
//@CrossOrigin
@RestController
@RequestMapping("/serviceedu/eduteacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("查询所有老师")
    @GetMapping("/findAll")
    public R list() {
        return R.ok().data("items", teacherService.list(null));
    }

    @CacheEvict(value = "banner",key = "'selectIndexList'")
    @ApiOperation("逻辑删除老师")
    @DeleteMapping("/delete/{id}")
    public R removeById(@ApiParam("逻辑删除老师的ID") @PathVariable("id") String id) {
        boolean b = teacherService.removeById(id);
        if (b) return R.ok();
        else return R.error();
    }

    @ApiOperation("分页查询老师")
    @GetMapping("/findPage/{current}/{limit}")
    public R page(@PathVariable("current") long current, @PathVariable("limit") long limit) {
        Page<EduTeacher> page = new Page<EduTeacher>(current, limit);
        teacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> list = page.getRecords();
        return R.ok().data("total", total).data("items", list);
    }

    @ApiOperation("条件分页查询老师")
    @SentinelResource(value = "teachersFindCondition",
            blockHandlerClass = TeacherFallbackHandler.class,
            blockHandler = "listConditionBlock",
            fallbackClass = TeacherFallbackHandler.class,
            fallback = "listConditionFallback")
    @PostMapping("/findCondition/{current}/{limit}")
    public R listCondition(@PathVariable("current") long current,
                           @PathVariable("limit") long limit,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {

        Random random = new Random();
        if (random.nextInt(10) > 7) {
            throw new RuntimeException();
        }
        Page<EduTeacher> page = new Page<EduTeacher>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        wrapper.orderByAsc("sort");
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) wrapper.like("name", name);
        if (!StringUtils.isEmpty(level)) wrapper.eq("level", level);
        if (!StringUtils.isEmpty(begin)) wrapper.ge("gmt_create", begin);
        if (!StringUtils.isEmpty(end)) wrapper.le("gmt_create", end);
        teacherService.page(page, wrapper);
        long total = page.getTotal();
        List<EduTeacher> list = page.getRecords();
        return R.ok().data("total", total).data("rows", list);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/add")
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher) {
        boolean b = teacherService.save(teacher);
        if (b) return R.ok();
        else return R.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/findbyid/{id}")
    public R getById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        if ("123456".equals(id)) throw new RuntimeException();
        if ("kkkkkk".equals(id)) throw new NullPointerException();
        if ("guliguli".equals(id)) throw new GuliException(500, "系统繁忙请稍后再试(谷粒异常)");
        return R.ok().data("item", teacher);
    }

    @CacheEvict(value = "banner",key = "'selectIndexList'")
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("/updatebyid/{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {
        teacher.setId(id);
        boolean b = teacherService.updateById(teacher);
        if (b) return R.ok();
        else return R.error();
    }
}
