package kmd.backend.magazine.controllers;

import org.springframework.web.bind.annotation.RestController;
import kmd.backend.magazine.dtos.FacultyRequestDto;
import kmd.backend.magazine.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<?> getAllFaculties() {
        return ResponseEntity.ok().body(facultyService.getAllFaculties());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFaculty(@ModelAttribute FacultyRequestDto facultyRequestDto) throws Exception {
        facultyService.saveFaculty(facultyRequestDto);
        return ResponseEntity.ok().body("Faculty Added");
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable int facultyId) {
        facultyService.deleteFacultyById(facultyId);
        return ResponseEntity.ok().body("Faculty deleted");
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<?> getFaculty(@PathVariable int facultyId) {
        return ResponseEntity.ok().body(facultyService.getFacultyById(facultyId));
    }

    @PostMapping("/update/{facultyId}")
    public ResponseEntity<?> updateFaculty(@ModelAttribute FacultyRequestDto facultyRequestDto,
            @PathVariable int facultyId) throws Exception {
        facultyService.updateFaculty(facultyRequestDto, facultyId);
        return ResponseEntity.ok().body("Faculty Updated!");
    }

    @GetMapping("/checkfacultyname")
    public ResponseEntity<?> checkUserName(@RequestParam("name") String facultyName) {
        return ResponseEntity.ok().body(facultyService.checkFacultyName(facultyName));
    }

}
