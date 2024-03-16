package kmd.backend.magazine.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import kmd.backend.magazine.dtos.FacultyRequestDto;
import kmd.backend.magazine.dtos.FacultyResponseDto;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.repos.FacultyRepo;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepo facultyRepo;

    public List<FacultyResponseDto> getAllgetFaculties() {
        List<FacultyResponseDto> facultyDtos = new ArrayList<>();
        for (Faculty faculty : facultyRepo.findAll()) {
            facultyDtos.add(new FacultyResponseDto(faculty.getId(), faculty.getName()));
        }
        return facultyDtos;
    }

    public Faculty getFacultyById(int facultyId) {
        return facultyRepo.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found!"));
    }

    public Faculty saveFaculty(FacultyRequestDto facultyRequestDto) throws Exception {
        Faculty faculty = new Faculty();
        List<Faculty> existingFaculty = facultyRepo.findByName(facultyRequestDto.getName());
        if (existingFaculty.isEmpty()) {
            faculty.setName(facultyRequestDto.getName());
            return facultyRepo.save(faculty);
        } else {
            throw new EntityAlreadyExistException(faculty.getName() + " is already exit ");
        }
    }

    public Faculty updateFaculty(FacultyRequestDto facultyRequestDto, int facultyId) throws Exception {
        checkFacultyName(facultyRequestDto.getName());
        Faculty existingFaculty = facultyRepo.findById(facultyId).get();
        if (existingFaculty == null) {
            throw new EntityNotFoundException("Faculty not found");
        }
        existingFaculty.setName(facultyRequestDto.getName());
        return facultyRepo.save(existingFaculty);
    }

    public String checkFacultyName(String name) {
        if (facultyRepo.findByName(name).size() > 0) {
            throw new EntityAlreadyExistException("Faculty");
        } else {
            return "Faculty is available";
        }
    }

    public void deleteFacultyById(int facultyId) {
        Faculty faculty = facultyRepo.findById(facultyId).get();
        if (faculty != null) {
            facultyRepo.deleteById(facultyId);
        } else {
            throw new EntityNotFoundException("Faculty not found");
        }
    }
}