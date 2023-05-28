package com.atguigu.serviceedu.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/serviceedu/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapter/{courseId}")
    public R getChapterByCourseID(@PathVariable("courseId") String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterByCourseID(courseId);
        return R.ok().data("chapterList", list);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean saved = eduChapterService.save(eduChapter);
        if (saved) return R.ok();
        else return R.error();
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean b = eduChapterService.updateById(eduChapter);
        if (b) return R.ok();
        else return R.error();
    }

    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapterById(@PathVariable("chapterId") String chapterId) {
        boolean b = eduChapterService.deleteChapter(chapterId);
        if (b) return R.ok();
        else return R.error();
    }
}

