package com.example.schedulerdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * All the scheduled methods should follow the following two criteria -
 * - The method should have a void return type.
 * - The method should not accept any arguments.
 */
@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task :: Execution Time - {}", formatter.format(LocalDateTime.now()));
    }

    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Fixed Delay Task :: Execution Time - {}", formatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
        /*
        Since the task itself takes 5 seconds to complete and we have specified a delay of 2 seconds between
        the completion of the last invocation and the start of the next,
        there will be a delay of 7 seconds between each invocation
         */
    }

    /**
     * You can use initialDelay parameter with fixedRate and fixedDelay to delay the first execution of the task with the specified number of milliseconds.
     * In the following example, the first execution of the task will be delayed by 5 seconds and then it will be executed normally at a fixed interval of 2 seconds
     */
    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
        logger.info("Fixed Rate Task with Initial Delay :: Execution Time - {}", formatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", formatter.format(LocalDateTime.now()));

    }
}
