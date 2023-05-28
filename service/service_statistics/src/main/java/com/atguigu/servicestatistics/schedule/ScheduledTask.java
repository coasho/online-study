package com.atguigu.servicestatistics.schedule;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.atguigu.servicestatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    StatisticsDailyService dailyService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void dailySta() {
        String dateStr = DateUtil.offsetDay(new Date(), -1).toDateStr();
        dailyService.createStatisticsByDay(dateStr);
    }
}
