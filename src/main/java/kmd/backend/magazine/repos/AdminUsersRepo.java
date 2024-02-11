package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.AdminUsers;

public interface AdminUsersRepo extends JpaRepository<AdminUsers, Integer> {
    
}
