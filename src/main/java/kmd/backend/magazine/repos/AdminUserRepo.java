package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.AdminUser;

public interface AdminUserRepo extends JpaRepository<AdminUser, Integer> {
    
}
