package kmd.backend.magazine.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.repos.FaculityRepo;

@Service
public class FaculityService {
    @Autowired
    private FaculityRepo faculityRepo;

    public List<Faculty> getAllgetFaculties() {
        return faculityRepo.findAll();
    }

    public Faculty saveFaculty(Faculty faculty) {
        List<Faculty> existingFaculty = faculityRepo.findByName(faculty.getName());
        if (existingFaculty.isEmpty()) {
            return faculityRepo.save(faculty);

        } else {
            throw new EntityAlreadyExistException(faculty.getName()+" Faculty exit ");
        }
    }

    public void deleteFacultyById(int facultyId) {

        Faculty faculty = faculityRepo.findById(facultyId).get();
        if (faculty==null) {
            faculityRepo.deleteById(facultyId);
        } else {
            throw new EntityNotFoundException("Faculty not found");
        }

    }
}