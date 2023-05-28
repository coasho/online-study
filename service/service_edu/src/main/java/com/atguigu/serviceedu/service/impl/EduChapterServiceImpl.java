package com.atguigu.serviceedu.service.impl;

import com.atguigu.commonutils.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.entity.chapter.VideoVo;
import com.atguigu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-18
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    public List<ChapterVo> getChapterByCourseID(String courseId) {
        ArrayList<ChapterVo> chapterVoList = new ArrayList<ChapterVo>();
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<EduChapter>();
        chapterWrapper.eq("course_id", courseId);
        chapterWrapper.orderByAsc("sort");
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);
        QueryWrapper<EduVideo> chapterVideoWrapperM = new QueryWrapper<EduVideo>();
        chapterVideoWrapperM.eq("course_id", courseId);
        chapterVideoWrapperM.orderByAsc("sort");
        List<EduVideo> videoList = eduVideoService.list(chapterVideoWrapperM);
        for (int i = 0; i < chapterList.size(); i++) {
            EduChapter eduChapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            ArrayList<VideoVo> videoVoList = new ArrayList<VideoVo>();
            for (int j = 0; j < videoList.size(); j++) {
                EduVideo eduVideo = videoList.get(j);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<EduChapter>();
        queryWrapper.eq("id", chapterId);
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        if (count > 0) throw new GuliException(20001, "该章节存在小节！");
        else {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }
}
