package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Faculty;

public interface FaculitiesRepo extends JpaRepository<Faculty, Integer> {
    
}
