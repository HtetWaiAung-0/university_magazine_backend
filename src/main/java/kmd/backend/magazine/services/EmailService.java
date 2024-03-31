package kmd.backend.magazine.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.models.Comment;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.ArticleRepo;
import kmd.backend.magazine.repos.FacultyRepo;
import kmd.backend.magazine.repos.UserRepo;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    public void sendEmail() throws MessagingException {

        List<Article> articles = articleRepo.findArticleByApproveStatus(Article.ApproveStatus.PENDING);
        System.out.println("article size == "+articles.size());
        for (Article article : articles) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate createDate = LocalDate.parse(article.getCreatedDate(), formatter);
            if (article.getComment().isEmpty() &&
                    (LocalDate.now().isAfter(createDate.plusDays(14)))) {

                // MimeMessage msg = javaMailSender.createMimeMessage();
                // SimpleMailMessage message = new SimpleMailMessage();
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                // Set the sender address and display name
                helper.setFrom("MagazineApp <hwaung1@kmd.edu.mm>");
                List<Faculty> faculties = facultyRepo.findFacultyByUserId(article.getUser().getId());
                for (Faculty faculty : faculties) {
                    List<User> users = userRepo.findCoordinatorUserByFacultyId(faculty.getId());

                    for (User user : users) {
                        if (user.getEmail() != null) {
                            helper.setTo(user.getEmail());
                            helper.setSubject("Article Pending for Approval");
                            helper.setText("This is the notify mail for article pending for approval with title : "
                                    + article.getTitle());
                            javaMailSender.send(message);
                        }

                    }

                }

            }else{
                System.out.println("out of condition");
            }

        }
        // // SimpleMailMessage message = new SimpleMailMessage();
        // MimeMessage message = javaMailSender.createMimeMessage();
        // MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // // Set the sender address and display name
        // helper.setFrom("MagazineApp <hwaung1@kmd.edu.mm>");
        // helper.setTo("pekarlay162@gmail.com");
        // helper.setSubject("This is testing.");
        // helper.setText("Testing mail sent by pekar.");
        // // message.setTo("pekarlay162@gmail.com");
        // // message.setSubject("This is testing.");
        // // message.setText("Testing mail sent by pekar.");
        // javaMailSender.send(message);
    }

}
