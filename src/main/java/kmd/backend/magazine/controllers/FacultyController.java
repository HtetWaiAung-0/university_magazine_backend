package kmd.backend.magazine.controllers;

import org.springframework.web.bind.annotation.RestController;


import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<?> getAllFaculties() {
        return ResponseEntity.ok().body(facultyService.getAllgetFaculties());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok().body(facultyService.saveFaculty(faculty));
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteUser(@PathVariable int facultyId) {
        facultyService.deleteFacultyById(facultyId);
        return ResponseEntity.ok().body("Faculty deleted");
    }

}
