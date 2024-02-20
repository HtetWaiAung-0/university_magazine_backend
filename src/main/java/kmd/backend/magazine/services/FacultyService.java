package kmd.backend.magazine.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Faculty;
import kmd.backend.magazine.repos.FacultyRepo;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepo facultyRepo;

    public List<Faculty> getAllgetFaculties() {
        return facultyRepo.findAll();
    }

    public Faculty saveFaculty(Faculty faculty) {
        List<Faculty> existingFaculty = facultyRepo.findByName(faculty.getName());
        if (existingFaculty.isEmpty()) {
            return facultyRepo.save(faculty);

        } else {
            throw new EntityAlreadyExistException(faculty.getName()+" Faculty exit ");
        }
    }

    public void deleteFacultyById(int facultyId) {

        Faculty faculty = facultyRepo.findById(facultyId).get();
        if (faculty==null) {
            facultyRepo.deleteById(facultyId);
        } else {
            throw new EntityNotFoundException("Faculty not found");
        }

    }
}