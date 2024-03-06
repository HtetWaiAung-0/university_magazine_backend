package kmd.backend.magazine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.AcademicYear;
import kmd.backend.magazine.services.AcademicYearService;

@RestController
@RequestMapping("/api/v1/academic-year")
public class AcademicYearController {
    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping
    public ResponseEntity<?> getAllAcademicYears() {
        return ResponseEntity.ok().body(academicYearService.getAllAcademicYears());
    }

    @GetMapping("/{academicYearId}")
    public ResponseEntity<?> getAcademicYear(@PathVariable int academicYearId) {
        return ResponseEntity.ok().body(academicYearService.getAcademicYear(academicYearId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAcademicYear(@RequestBody AcademicYear academicYear) {
        academicYearService.addAcademicYear(academicYear);
        return ResponseEntity.ok().body("Academic Year added");
    }

    @PostMapping("/update/{academicYearId}")
    public ResponseEntity<?> updateAcademicYear(@PathVariable int academicYearId, @RequestBody AcademicYear academicYear) {
        academicYearService.updatAcademicYear(academicYearId, academicYear);
        return ResponseEntity.ok().body("Academic Year updated");
    }
}
