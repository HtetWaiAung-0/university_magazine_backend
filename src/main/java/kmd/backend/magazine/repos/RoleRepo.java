package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
}
