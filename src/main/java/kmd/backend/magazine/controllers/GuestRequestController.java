package kmd.backend.magazine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.GuestRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/guest")
public class GuestRequestController {
    
    @GetMapping()
    public String getGuestRequest() {
        return "Guest Request";
    }

    @PostMapping("/register")
    public ResponseEntity<?> postMethodName(@ModelAttribute GuestRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok().body("Guest Request");
    }
    
}
