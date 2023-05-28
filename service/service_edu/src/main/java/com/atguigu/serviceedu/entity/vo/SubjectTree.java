package com.atguigu.serviceedu.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubjectTree {
    private String id;
    private String title;
    private List<SubjectTree> subjectsTreeChild;
}
