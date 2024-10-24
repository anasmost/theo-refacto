package com.nimbleways.springboilerplate.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.PostPersist;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ProductListener {
    private TaskScheduler taskScheduler;

    ProductListener(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    // @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS, initialDelay = 1)
    @PostPersist
    public void decrementLeadTime(Product p) {
        final var NOW = LocalDateTime.now();

        taskScheduler.scheduleAtFixedRate(() -> {
            p.setLeadTime(p.getLeadTime() - 1);
        }, NOW.plusDays(1).toInstant(ZoneOffset.systemDefault().getRules().getOffset(NOW)),
                Duration.ofDays(p.getLeadTime()));
    }
}
