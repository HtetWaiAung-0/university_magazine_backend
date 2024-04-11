package kmd.backend.magazine.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kmd.backend.magazine.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
  Optional<User> findByNameAndDeleteStatus(String userName, boolean deleteStatus);

  User findByEmailAndDeleteStatus(String email, boolean deleteStatus);

  Optional<User> findByName(String userName);

  User findByIdAndDeleteStatus(int id, boolean deleteStatus);

  List<User> findByDeleteStatus(boolean deleteStatus);

  @Query(value = "select u.* from faculty f\n" + //
      "inner join users u on f.id = u.faculty_id\n" + //
      "where f.id = :facultyId and u.role = 'COORDINATOR'", nativeQuery = true)
  List<User> findCoordinatorUserByFacultyId(@Param("facultyId") int facultyId);

  @Query(value = "select * from users where faculty_id = :facultyId and delete_status = false", nativeQuery = true)
  List<User> findUsersByFacultyId(@Param("facultyId") int facultyId);

}
