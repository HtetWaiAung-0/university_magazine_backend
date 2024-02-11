package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    
}
