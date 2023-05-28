package com.atguigu.eduservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String fileName = "C:/Users/WENXUAN WANG/Desktop/write.xlsx";
        //EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getDataList());

        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet("学生列表").doRead();
    }

    private static List<DemoData> getDataList() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }
}
