package kmd.backend.magazine.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedularTask {

    // @Scheduled(fixedRate = 5000) // Execute every 5 seconds
    // public void task1() {
    //     System.out.println("Task 1 executed at fixed rate");
    // }

    @Scheduled(cron = "0 0 7 * * *") // Execute everyday at midnight
    public void task2() {
        System.out.println("Task 2 executed at midnight");
    }
}
