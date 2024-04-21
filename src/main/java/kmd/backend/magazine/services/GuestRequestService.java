package kmd.backend.magazine.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import kmd.backend.magazine.dtos.UserRequestDto;
import kmd.backend.magazine.models.GuestRequest;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.GuestRequestRepo;

@Service
public class GuestRequestService {
    @Autowired
    private GuestRequestRepo guestRequestRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public GuestRequest saveGuestRequest(GuestRequest guestRequest) throws Exception {
        if (guestRequestRepo.findByStatusAndEmail(GuestRequest.Status.valueOf("PENDING"), guestRequest.getEmail())
                .size() > 0) {
            throw new Exception("Email already exist!");
        }

        try {
            if(guestRequest.getFacultyId()==0){
                throw new EntityNotFoundException("Faculty does not exist!");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            guestRequest.setStatusDate(LocalDate.now().format(formatter));
            return guestRequestRepo.save(guestRequest);
        } catch(EntityNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Faculty does not exist!");
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Guest Register Fail!");
        }

    }

    public List<GuestRequest> getAllGuestRequests() throws Exception {
        try {
            return guestRequestRepo.findByStatus(GuestRequest.Status.valueOf("PENDING"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Get Guest Request Fail!");
        }

    }

    public void approveGuestRequest(int guestRequestId) throws Exception {

        try {
            GuestRequest guestRequest = guestRequestRepo.findById(guestRequestId).get();
            UserRequestDto userRequestDto = new UserRequestDto();

            userRequestDto.setEmail(guestRequest.getEmail());
            userRequestDto.setName(guestRequest.getEmail().split("@")[0]);
            userRequestDto.setRole("GUEST");
            String randomWord = commonService.generateRandomWord(10);
            userRequestDto.setPassword(randomWord);
            userRequestDto.setFaculty(guestRequest.getFacultyId());

            // Save user in user table
            User user = userService.saveUser(userRequestDto);
            guestRequest.setUserId(user.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            guestRequest.setStatusDate(LocalDate.now().format(formatter));
            guestRequest.setStatus(GuestRequest.Status.valueOf("ACTIVE"));
            guestRequestRepo.save(guestRequest);

            emailService.sendEmailForCreatedGuestAcc(user, randomWord);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Approve Guest Request Fail!");
        }

    }

    public void rejectGuestRequest(int guestRequestId) throws Exception {
        try {
            GuestRequest guestRequest = guestRequestRepo.findById(guestRequestId).get();
            guestRequest.setStatus(GuestRequest.Status.valueOf("REJECT"));
            guestRequestRepo.save(guestRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Reject Guest Request Fail!");
        }
    }

    public void checkExpriedGuestAccount() throws Exception {
        try {
            List<GuestRequest> guestRequests = guestRequestRepo.findByStatus(GuestRequest.Status.valueOf("ACTIVE"));
            for (GuestRequest guestRequest : guestRequests) {
                if (guestRequest.getStatusDate()
                        .compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))) < 14) {
                    guestRequest.setStatus(GuestRequest.Status.valueOf("EXPIRED"));
                    guestRequest.setStatusDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                    guestRequestRepo.save(guestRequest);
                    try {
                        userService.deleteUser(guestRequest.getUserId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("User delete Fail!");
                    }
                    emailService.sendEmail(guestRequest.getEmail(), "Guest Account Expired Notification",
                            "Your guest Account<" + guestRequest.getEmail() + ">for Magazine is expired.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Set Guest Expired Fail!");
        }
    }

}
