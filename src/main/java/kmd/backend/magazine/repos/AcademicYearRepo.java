package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.AcademicYear;


public interface AcademicYearRepo extends JpaRepository<AcademicYear, Integer> {
  List<AcademicYear> findByNameAndDeleteStatus(String userName,boolean deleteStatus);
  List<AcademicYear> findByName(String userName);
  AcademicYear findByIdAndDeleteStatus(int id, boolean deleteStatus);
  List<AcademicYear> findByDeleteStatus(boolean deleteStatus);
}
