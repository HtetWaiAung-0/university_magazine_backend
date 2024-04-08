package kmd.backend.magazine.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import kmd.backend.magazine.services.EmailService;

@Component
public class SchedularTask {

    @Autowired
    private EmailService emailService;

    // @Scheduled(fixedRate = 50000) // Execute every 5 seconds
    // public void task1() {
    //     try {
    //         emailService.sendEmail();
    //         System.out.println("Email sent successfully");
    //     } catch (MessagingException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }

    @Scheduled(cron = "0 0 7 * * *")
    public void NotifyMailSender() {
        try {
            emailService.sendEmailForNotApproveArticle();
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
