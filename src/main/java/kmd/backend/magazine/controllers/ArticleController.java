package kmd.backend.magazine.controllers;

import java.util.List;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.services.ArticleService;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public ResponseEntity<Object> getAllArticles() {
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Object> getArticle(@PathVariable int articleId) {
        
        try{
            return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticle(articleId));
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }   
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadArticle(@RequestBody Article article) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(articleService.uploadArticle(article));
        }catch(EntityAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Object> deleteArticle(@PathVariable int articleId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(articleService.deleteArticle(articleId));
        }catch(EntityAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
