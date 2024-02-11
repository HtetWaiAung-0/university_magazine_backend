package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Faculties;

public interface Faculities extends JpaRepository<Faculties, Integer> {
    
}
