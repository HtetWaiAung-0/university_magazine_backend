package kmd.backend.magazine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.GuestRequest;
import kmd.backend.magazine.services.GuestRequestService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/guest")
public class GuestRequestController {

    @Autowired
    private GuestRequestService guestRequestService;

    @GetMapping()
    public String getGuestRequest() {
        return "Guest Request";
    }

    @PostMapping("/register")
    public ResponseEntity<?> guestRegister(@ModelAttribute GuestRequest request) throws Exception {
        guestRequestService.saveGuestRequest(request);
        return ResponseEntity.ok().body("Guest Request Success.");
    }

    @PostMapping("/approve/{guestRequestId}")
    public ResponseEntity<?> guestApprove(@PathVariable int guestRequestId) throws Exception {
        guestRequestService.approveGuestRequest(guestRequestId);
        return ResponseEntity.ok().body("Guest Request Approve Success.");
    }

}
