package com.atguigu.serviceedu.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.vo.SubjectTree;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/serviceedu/edu-subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file);
        return R.ok();
    }

    @GetMapping("/getSubjectList")
    public R getSubjectList() {
        List<EduSubject> list = eduSubjectService.list(null);
        ArrayList<SubjectTree> subjectTrees = setList(list,"0");
        return R.ok().data("list", subjectTrees);
    }
    private ArrayList<SubjectTree> setList(List<EduSubject> list_all,String parentId){
        ArrayList<SubjectTree> trees = new ArrayList<>();
        List<EduSubject> list=getC_list(list_all,parentId);
        if (list.isEmpty()) return null;
        for (int i = 0; i < list.size(); i++) {
            ArrayList<SubjectTree> treeTwos = this.setList(list_all,list.get(i).getId());
            SubjectTree treeOne = new SubjectTree();
            BeanUtils.copyProperties(list.get(i),treeOne);
            treeOne.setSubjectsTreeChild(treeTwos);
            trees.add(treeOne);
        }
        return trees;
    }
    private List<EduSubject> getC_list(List<EduSubject> list,String parentId){
        List<EduSubject> c_list=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(parentId.equals(list.get(i).getParentId())){
                c_list.add(list.get(i));
            }
        }
        return c_list;
    }
}

