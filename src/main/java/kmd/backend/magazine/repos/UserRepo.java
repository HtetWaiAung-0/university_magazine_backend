package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
  List<User> findByName(String userName);
    
}
