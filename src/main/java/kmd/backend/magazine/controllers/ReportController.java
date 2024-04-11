package kmd.backend.magazine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.services.ReportService;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserReport() {
        return ResponseEntity.ok().body(reportService.getUserReport());
    }

    @GetMapping("/faculty")
    public ResponseEntity<?> getFacultyReport() {
        return ResponseEntity.ok().body(reportService.getFacultyReport());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReport() {
        return ResponseEntity.ok().body(reportService.getReport());
    }
    
}
