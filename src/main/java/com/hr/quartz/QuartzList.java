package com.hr.quartz;

import com.hr.Overall.Global;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuartzList {

//    @Scheduled(cron = Global.time)
//    @Async
    public void SqlLogin() {
//        System.out.println("定时任务：" + Util.GetTime());
    }
}