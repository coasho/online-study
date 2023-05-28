package com.atguigu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.excel.ExcelSubjectData;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    private EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        String oneSubjectName = excelSubjectData.getOneSubjectName();
        EduSubject oneSubject = existOneSubject(oneSubjectName);
        String parentId;
        if (oneSubject == null) {
            EduSubject oneEduSubject = new EduSubject().setTitle(oneSubjectName).setParentId("0");
            eduSubjectService.save(oneEduSubject);
            parentId=oneEduSubject.getId();
        } else parentId = oneSubject.getId();
        String twoSubjectName = excelSubjectData.getTwoSubjectName();
        if (existTwoSubject(twoSubjectName) == null) {
            EduSubject twoEduSubject = new EduSubject().setTitle(twoSubjectName).setParentId(parentId);
            eduSubjectService.save(twoEduSubject);
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    private EduSubject existTwoSubject(String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("title", name);
        wrapper.ne("parent_id", 0);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }
}
