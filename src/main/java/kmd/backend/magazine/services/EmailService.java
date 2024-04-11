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

    public void sendEmailForNotApproveArticle() throws MessagingException {

        List<Article> articles = articleRepo.findArticleByApproveStatus(Article.ApproveStatus.PENDING);
        for (Article article : articles) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate createDate = LocalDate.parse(article.getCreatedDate(), formatter);
            if (article.getComment().isEmpty() &&
                    (LocalDate.now().isAfter(createDate.plusDays(14)))) {

                List<Faculty> faculties = facultyRepo.findFacultyByUserId(article.getUser().getId());
                for (Faculty faculty : faculties) {
                    List<User> users = userRepo.findCoordinatorUserByFacultyId(faculty.getId());

                    for (User user : users) {
                        if (user.getEmail() != null) {
                            sendEmail(user.getEmail(), "Article Pending for Approval",
                                    "This is the notify mail for article pending for approval with title : "
                                            + article.getTitle());
                        }
                    }
                }

            }

        }
    }

    public void sendEmailForCreatedGuestAcc(User user, String password) throws MessagingException {
        String subject = "Approved Guest Account";
        String text = "Your account has been created. \n UserName => " + user.getName() + "\n Password => " + password;

        if (user.getEmail() != null) {
            sendEmail(user.getEmail(), subject, text);
        } else {
            throw new MessagingException();
        }

    }


    public void sendEmail(String toMail, String subject, String text) throws MessagingException {

        if (!toMail.isEmpty() && !subject.isEmpty() && !text.isEmpty()) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("MagazineApp <hwaung1@kmd.edu.mm>");
            helper.setTo(toMail);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(message);
        } else {
            throw new MessagingException("Mail sent error! Some parameter is missing!");
        }

    }

}
