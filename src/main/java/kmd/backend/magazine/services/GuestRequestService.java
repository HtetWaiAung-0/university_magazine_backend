package kmd.backend.magazine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.models.GuestRequest;
import kmd.backend.magazine.repos.GuestRequestRepo;

@Service
public class GuestRequestService {
    @Autowired
    private GuestRequestRepo guestRequestRepo;

    public void saveGuestRequest(GuestRequest guestRequest) throws Exception {
        if(guestRequestRepo.findByStatusAndEmail(GuestRequest.Status.valueOf("PENDING"), guestRequest.getEmail()).size() > 0) {
            throw new Exception("Email already exist!");

        }

        try {
            guestRequestRepo.save(guestRequest);
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

    public void approveGuestRequest(int guestRequestId) throws Exception {
        try {
            GuestRequest guestRequest = guestRequestRepo.findById(guestRequestId).get();
            guestRequest.setStatus(GuestRequest.Status.valueOf("ACTIVE"));
            guestRequestRepo.save(guestRequest);
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
