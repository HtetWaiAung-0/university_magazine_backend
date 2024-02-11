package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Article;

public interface ArticleRepo extends JpaRepository<Article, Integer> {
    
}
