package kmd.backend.magazine.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.repos.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articlesRepo;

    
    public Article uploadArticle(Article article) {
        if(article == null){
            throw new IllegalArgumentException("Article is null");
        }
        return articlesRepo.save(article);
    }

    public List<Article> getAllArticles() {
        return articlesRepo.findAll();
    }
}
