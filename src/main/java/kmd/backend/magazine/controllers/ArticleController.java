package kmd.backend.magazine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.services.ArticleService;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticle(@PathVariable int articleId) {
        Article articel = articleService.getArticle(articleId);
        if(articel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(articel);
    }

    @PostMapping("/upload")
    public ResponseEntity<Article> uploadArticle(@RequestBody Article article) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.uploadArticle(article));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable int articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.deleteArticle(articleId));
        
    }
}
