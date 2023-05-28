package com.atguigu.serviceedu.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.commonutils.CourseInfoForm;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQuery;
import com.atguigu.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/serviceedu/edu-course")
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = eduCourseService.saveCourseInfo(courseInfoForm);
        if (id != null) return R.ok().data("id", id);
        else return R.error().message("课程保存失败");
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfoForm);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String id = eduCourseService.updateCourseInfo(courseInfoForm);
        if (id != null) return R.ok().data("id", id);
        else return R.error().message("课程保存失败");
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("/course-publish-info/{id}")
    public R getCoursePublishVoById(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoById(id);
        return R.ok().data("item", coursePublishVo);
    }
    @CacheEvict(value = "banner",key = "'selectIndexList'")
    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable String id) {
        eduCourseService.publishCourseById(id);
        return R.ok();
    }

    @PostMapping("/getCourseListByCondition/{current}/{limit}")
    public R getCourseListByCondition(@PathVariable("current") Long current,
                                      @PathVariable("limit") Long limit,
                                      @RequestBody CourseQuery courseQuery) {
        Page<EduCourse> eduCoursePage = new Page<EduCourse>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>();
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String status = courseQuery.getStatus();
        wrapper.orderByDesc("gmt_modified");
        if (!StringUtils.isEmpty(title)) wrapper.like("title", title);
        if (!StringUtils.isEmpty(teacherId)) wrapper.eq("teacher_id", teacherId);
        if (!StringUtils.isEmpty(subjectId)) wrapper.eq("subject_id", subjectId);
        if (!StringUtils.isEmpty(subjectParentId)) wrapper.eq("subject_parent_id", subjectParentId);
        if (!StringUtils.isEmpty(status)) wrapper.eq("status", status);
        eduCourseService.page(eduCoursePage, wrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> courseList = eduCoursePage.getRecords();
        return R.ok().data("items", courseList).data("total", total);
    }
    @CacheEvict(value = "banner",key = "'selectIndexList'")
    @DeleteMapping("/deleteCourseById/{id}")
    public R deleteCourseById(@ApiParam(name = "id", value = "课程ID", required = true) @PathVariable String id) {
        boolean b = eduCourseService.deleteById(id);
        if (b) return R.ok();
        else return R.error().message("删除失败");
    }
}

