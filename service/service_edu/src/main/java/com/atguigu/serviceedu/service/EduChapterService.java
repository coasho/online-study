package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterByCourseID(String courseId);

    boolean deleteChapter(String chapterId);
}
