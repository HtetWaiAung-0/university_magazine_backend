package kmd.backend.magazine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.AdminUser;


public interface AdminUserRepo extends JpaRepository<AdminUser, Integer> {
     List<AdminUser> findById(int id);
     List<AdminUser> findByName(String name);
}
