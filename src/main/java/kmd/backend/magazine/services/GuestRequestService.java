package kmd.backend.magazine.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.dtos.UserRequestDto;
import kmd.backend.magazine.models.GuestRequest;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.GuestRequestRepo;

@Service
public class GuestRequestService {
    @Autowired
    private GuestRequestRepo guestRequestRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public GuestRequest saveGuestRequest(GuestRequest guestRequest) throws Exception {
        if(guestRequestRepo.findByStatusAndEmail(GuestRequest.Status.valueOf("PENDING"), guestRequest.getEmail()).size() > 0) {
            throw new Exception("Email already exist!");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            guestRequest.setStatusDate(LocalDate.now().format(formatter));
            System.out.println("testing");
            return guestRequestRepo.save(guestRequest);
        } catch (Exception e) {
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

    public static String generateRandomWord(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public void approveGuestRequest(int guestRequestId) throws Exception {

        
        try {
            GuestRequest guestRequest = guestRequestRepo.findById(guestRequestId).get();
            UserRequestDto userRequestDto = new UserRequestDto();

            userRequestDto.setEmail(guestRequest.getEmail());
            userRequestDto.setName(guestRequest.getEmail().split("@")[0]);
            userRequestDto.setRole("GUEST");
            String randomWord = generateRandomWord(6);
            userRequestDto.setPassword(randomWord);
            userRequestDto.setFaculty(guestRequest.getFacultyId());

            //Save user in user table
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
            guestRequest.setStatus(GuestRequest.Status.valueOf("REJECTED"));
            guestRequestRepo.save(guestRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Reject Guest Request Fail!");
        }
    }
    


}
