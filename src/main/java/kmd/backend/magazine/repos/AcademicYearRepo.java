package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.AcademicYear;


public interface AcademicYearRepo extends JpaRepository<AcademicYear, Integer> {
    List<AcademicYear> findByName(String name);
}
