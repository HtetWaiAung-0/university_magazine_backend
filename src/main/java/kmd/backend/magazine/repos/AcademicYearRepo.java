package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.AcademicYear;

public interface AcademicYearRepo extends JpaRepository<AcademicYear, Integer> {
    
}
