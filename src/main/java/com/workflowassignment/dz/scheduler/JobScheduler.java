package com.workflowassignment.dz.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
@EnableScheduling
@ComponentScan
@Component
public class JobScheduler {

    private static AnnotationConfigApplicationContext CONTEXT = null;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        return threadPoolTaskScheduler;
    }

    public void start(Runnable task, String scheduleExpression) throws Exception {
        scheduler.schedule(task, new CronTrigger(scheduleExpression));
    }

    public void start(Runnable task, Long delay) throws Exception {
        scheduler.scheduleWithFixedDelay(task, delay);
    }

    public void start(Runnable task, Date initialDelay, Long delay) throws Exception {
        scheduler.scheduleWithFixedDelay(task, initialDelay, delay);
    }

    public void stopAll() {
        scheduler.shutdown();
    }
}