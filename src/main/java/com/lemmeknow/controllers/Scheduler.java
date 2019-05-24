package com.lemmeknow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

public class Scheduler implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Scheduler.class);
    ScheduledFuture scheduledFuture;
    TaskScheduler taskScheduler;
    //this method will kill previous scheduler if exists and will create a new scheduler with given cron expression
    public  void reSchedule(String cronExpressionStr){
        if(taskScheduler == null){
            this.taskScheduler = new ConcurrentTaskScheduler();
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
        this.scheduledFuture = this.taskScheduler.schedule(this, new CronTrigger(cronExpressionStr));
        logger.info("Расписание переустановлено на: "+ cronExpressionStr);
    }

    /*
    //https://dzone.com/articles/schedulers-in-java-and-spring
        @Scheduled(cron = CRON_EXP)
        public void parserScheduler(){
            logger.info("time to parse everything");
            try {
                new SchedulerController().parseAll();
                logger.info("everything is OK");
            } catch (IOException e) {
                logger.info("something gone wrong");
                e.printStackTrace();
            }

        }
    // для тестирования
        @Scheduled(fixedRate = 6000)
        public void testScheduler(){
            //parseSelected("https://www.afisha.ru");
            System.out.println("Now: " + new Date());
        }*/
//second, minute, hour, day of month, month, day(s) of week
    public static String dateToCron(Date date){
        LocalDateTime localDate = new java.sql.Timestamp(date.getTime()).toLocalDateTime();
        String cronExp = localDate.getSecond() + " " + localDate.getMinute() + " " + localDate.getHour() + " ? " + localDate.getMonthValue() + " ?";

        logger.debug("cronExp= ", cronExp);
        return cronExp;
    }



    @Override
    public void run() {
        logger.info("time to parse everything");
        try {
            new SchedulerController().parseAll();
            logger.info("everything is OK");
        } catch (IOException e) {
            logger.info("something gone wrong");
            e.printStackTrace();
        }
    }
}


