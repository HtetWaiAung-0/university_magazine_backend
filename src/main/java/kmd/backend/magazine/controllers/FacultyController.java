package kmd.backend.magazine.controllers;

import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.services.FaculityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/faculty")
public class FacultyController {

    @Autowired
    private FaculityService faculityService;

    @GetMapping
    public List<Faculty> getAllFaculties() {
        return faculityService.getAllgetFaculties();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFaculty(@RequestBody Faculty faculty) {
        try {
            faculityService.saveFaculty(faculty);
            return ResponseEntity.ok().body("Faculty added");
        } catch (EntityAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<String> deleteUser(@PathVariable int facultyId) {
        try {
            faculityService.deleteFacultyById(facultyId);
            return ResponseEntity.ok().body("Faculty deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
