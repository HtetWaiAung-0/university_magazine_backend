package kmd.backend.magazine.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import kmd.backend.magazine.services.EmailService;
import kmd.backend.magazine.services.GuestRequestService;

@Component
public class SchedularTask {

    @Autowired
    private EmailService emailService;
    @Autowired
    private GuestRequestService guestRequestService;

    @Scheduled(cron = "0 0 7 * * *")
    public void NotifyMailSender() {
        try {
            emailService.sendEmailForNotApproveArticle();
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void checkExpriedGuestAccount() throws Exception {
        try {
            guestRequestService.checkExpriedGuestAccount();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Check expried guest account fail");
        }
    }

}
