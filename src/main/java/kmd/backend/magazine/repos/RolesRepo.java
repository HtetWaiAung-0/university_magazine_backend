package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Roles;

public interface RolesRepo extends JpaRepository<Roles, Integer> {
    
}
