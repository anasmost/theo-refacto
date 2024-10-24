package com.nimbleways.springboilerplate;

import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
// @ComponentScan(basePackageClasses = ProductListener.class)
public class TaskSchedulerConfig {

    @Bean
    TaskScheduler threadPoolTaskScheduler() {
        return new TaskSchedulerBuilder().poolSize(1).build();
    }
}
