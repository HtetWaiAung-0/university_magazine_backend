package kmd.backend.magazine.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.AcademicYear;
import kmd.backend.magazine.services.AcademicYearService;

@RestController
@RequestMapping("/api/v1/academic-year")
public class AcademicYearController {
    @Autowired
    private AcademicYearService academicYearService;

    @GetMapping()
    public ResponseEntity<Object> getAllAcademicYears() {
        return ResponseEntity.ok().body(academicYearService.getAllAcademicYears());
    }

    @GetMapping("/{academicYearId}")
    public ResponseEntity<Object> getAcademicYear(@PathVariable int academicYearId) {
        try{
            return ResponseEntity.ok().body(academicYearService.getAcademicYear(academicYearId));
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAcademicYear(@RequestBody AcademicYear academicYear) {
        try{
            return ResponseEntity.ok().body(academicYearService.addAcademicYear(academicYear));
        }catch(EntityAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
