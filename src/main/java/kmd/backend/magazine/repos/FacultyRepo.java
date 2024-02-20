package kmd.backend.magazine.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Faculty;

public interface FacultyRepo extends JpaRepository<Faculty, Integer> {
    List<Faculty>findByName(String facultyName);
}
