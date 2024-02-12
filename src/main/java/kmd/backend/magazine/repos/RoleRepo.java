package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    List<Role> findByRole(String role);
    //List<Role> findByColumnNameIsNull();
}
