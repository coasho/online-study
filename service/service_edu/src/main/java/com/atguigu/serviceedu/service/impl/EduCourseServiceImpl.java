package com.atguigu.serviceedu.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduCourseDescription;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.commonutils.CourseInfoForm;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduSubjectService eduSubjectService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private VodVideoService vodVideoService;

    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse course = new EduCourse();
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm, course);
        BeanUtils.copyProperties(courseInfoForm, courseDescription);
        int insert = baseMapper.insert(course);
        if (insert == 0) throw new GuliException(20001, "添加课程失败");
        courseDescription.setId(course.getId());
        eduCourseDescriptionService.save(courseDescription);
        return course.getId();
    }

    public CourseInfoForm getCourseInfo(String courseId) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        if (eduCourse == null) return null;
        BeanUtils.copyProperties(eduCourse, courseInfoForm);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription, courseInfoForm);
        return courseInfoForm;
    }

    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoForm, courseDescription);
        int num = baseMapper.updateById(eduCourse);
        courseDescription.setId(courseInfoForm.getId());
        eduCourseDescriptionService.updateById(courseDescription);
        return courseInfoForm.getId();
    }

    public CoursePublishVo getCoursePublishVoById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        BigDecimal eduCoursePrice = eduCourse.getPrice();
        CoursePublishVo coursePublishVo = new CoursePublishVo();
        BeanUtils.copyProperties(eduCourse, coursePublishVo);
        coursePublishVo.setPrice(String.valueOf(eduCoursePrice));
        coursePublishVo.setTeacherName(eduTeacherService.getById(eduCourse.getTeacherId()).getName());
        coursePublishVo.setSubjectLevelOne(eduSubjectService.getById(eduCourse.getSubjectId()).getTitle());
        coursePublishVo.setSubjectLevelTwo(eduSubjectService.getById(eduCourse.getSubjectParentId()).getTitle());
        return coursePublishVo;
    }

    public Boolean publishCourseById(String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        Integer count = baseMapper.updateById(course);
        return null != count && count > 0;
    }

    public boolean deleteById(String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>();
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>();
        videoQueryWrapper.eq("course_id", id);
        chapterQueryWrapper.eq("course_id", id);
        videoQueryWrapper.select("video_source_id");
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper);
        System.out.println(videoList);
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < videoList.size(); i++) {
            ids.add(videoList.get(i).getVideoSourceId());
        }
        String join = StringUtils.join(ids.toArray(), ",");
        R r;
        if (!StringUtils.isEmpty(join)) {
            r = vodVideoService.removeVideo(join);
        } else r = R.ok();
        eduVideoService.remove(videoQueryWrapper);
        eduChapterService.remove(chapterQueryWrapper);
        eduCourseDescriptionService.removeById(id);
        int i = baseMapper.deleteById(id);
        return i > 0 && r.getSuccess();
    }

    /**
     * 根据讲师id查询当前讲师的课程列表
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> selectByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");
        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }
    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
