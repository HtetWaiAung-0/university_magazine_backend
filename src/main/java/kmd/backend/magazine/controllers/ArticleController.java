package kmd.backend.magazine.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.services.ArticleService;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public ResponseEntity<?> getAllArticles() {
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable int articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticle(articleId));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadArticle(@RequestBody Article article) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.uploadArticle(article));
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        articleService.fileLocalUpload(file);
        return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable int articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.deleteArticle(articleId));
    }

}
