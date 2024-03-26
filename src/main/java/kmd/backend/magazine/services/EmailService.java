package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() throws MessagingException {
        //SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        // Set the sender address and display name
        helper.setFrom("MagazineApp <hwaung1@kmd.edu.mm>");
        helper.setTo("pekarlay162@gmail.com");
        helper.setSubject("This is testing.");
        helper.setText("Testing mail sent by pekar.");
        // message.setTo("pekarlay162@gmail.com");
        // message.setSubject("This is testing.");
        // message.setText("Testing mail sent by pekar.");
        javaMailSender.send(message);
    }

}
