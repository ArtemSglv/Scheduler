package com.lemmeknow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Configuration
@EnableScheduling
@Scope(value = "session")
@Component(value = "scheduler")
public class Scheduler {
    //todo: remove localhost
    private Logger logger = LoggerFactory.getLogger(Scheduler.class);
    static final String CRON_EXP = "0 0 3 * * ?";

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
    /*@Scheduled(fixedRate = 6000)
    public void testScheduler(){
        //parseSelected("https://www.afisha.ru");
        System.out.println("Now: " + new Date());
    }*/





}


