package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.AcademicYear;
import kmd.backend.magazine.services.AcademicYearService;


public interface AcademicYearRepo extends JpaRepository<AcademicYear, Integer> {
    List<AcademicYear> findByNameAndDeleteStatus(String name,boolean deleteStatus);
    List<AcademicYear> findByName(String name);

}
