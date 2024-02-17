package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.AcademicYear;
import java.util.List;


public interface AcademicYearRepo extends JpaRepository<AcademicYear, Integer> {
    List<AcademicYear> findByName(String name);
}
