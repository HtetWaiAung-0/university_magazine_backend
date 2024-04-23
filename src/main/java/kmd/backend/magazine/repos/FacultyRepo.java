package kmd.backend.magazine.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kmd.backend.magazine.models.Faculty;

public interface FacultyRepo extends JpaRepository<Faculty, Integer> {
    List<Faculty> findByName(String facultyName);

    @Query(value = "select f.* from faculty f\n" + //
            "inner join users u on f.id = u.faculty_id where u.id = :userId", nativeQuery = true)
    List<Faculty> findFacultyByUserId(@Param("userId") int userId);

    @Query(value = "select * from faculty order by id where delete_status = false", nativeQuery = true)
    List<Faculty> findAllFaculties();

}
