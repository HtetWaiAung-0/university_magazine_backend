package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Articles;

public interface ArticlesRepo extends JpaRepository<Articles, Integer> {
    
}
