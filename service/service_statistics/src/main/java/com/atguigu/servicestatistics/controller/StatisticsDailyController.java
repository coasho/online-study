package com.atguigu.servicestatistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicestatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-05-14
 */
@RestController
@RequestMapping("/servicestatistics")
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService dailyService;

    @PostMapping("/statistics-daily/{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }
    @GetMapping("/show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

