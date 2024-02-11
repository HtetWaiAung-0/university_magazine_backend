package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Facultie;

public interface Faculitie extends JpaRepository<Facultie, Integer> {
    
}
