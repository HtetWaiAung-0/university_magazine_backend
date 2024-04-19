package kmd.backend.magazine.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmd.backend.magazine.dtos.ArticelRequestDto;
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

    @GetMapping("/byFaculty/{facultyId}")
    public ResponseEntity<?> getArticlesByFacultyId(@PathVariable int facultyId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticlesByFacultyId(facultyId));
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<?> getArticlesByUserId(@PathVariable int userId) {
        return ResponseEntity.ok().body(articleService.getArticlesByUserId(userId));
    }

    @GetMapping("/ByStatus/{status}")
    public ResponseEntity<?> getArticlesByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(articleService.getArticlesByStatus(status));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addArticle(@ModelAttribute ArticelRequestDto articelRequestDto) throws Exception {
        articleService.saveArticle(articelRequestDto);
        return ResponseEntity.ok().body("Article added");
    }

    @PostMapping("/update/{articleId}")
    public ResponseEntity<?> updateArticle(@ModelAttribute ArticelRequestDto articelRequestDto,
            @PathVariable int articleId) throws Exception {
        articleService.updateArticle(articelRequestDto, articleId);
        return ResponseEntity.ok().body("Article updated");
    }

    @PostMapping("/approve/{articleId}")
    public ResponseEntity<?> approveArticle(@PathVariable int articleId) throws Exception {
        articleService.setApproveArticle(articleId, Article.ApproveStatus.APPROVED);
        return ResponseEntity.ok().body("Article approved");
    }

    @PostMapping("/reject/{articleId}")
    public ResponseEntity<?> rejectArticle(@PathVariable int articleId) throws Exception {
        articleService.setApproveArticle(articleId, Article.ApproveStatus.REJECTED);
        return ResponseEntity.ok().body("Article rejected");
    }

    @GetMapping("/file/download/{articleId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int articleId) throws Exception {
        Article article = articleService.getArticleRaw(articleId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(article.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + article.getFileName()
                                + "\"")
                .body(new ByteArrayResource(article.getFileData()));
    }

    @GetMapping("/coverPhoto/download/{articleId}")
    public ResponseEntity<Resource> downloadCoverPhoto(@PathVariable int articleId) throws Exception {
        Article article = articleService.getArticleRaw(articleId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(article.getCoverPhotoType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + article.getCoverPhotoName()
                                + "\"")
                .body(new ByteArrayResource(article.getCoverPhotoData()));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable int articleId) throws Exception {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok().body("Article deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<?> getArticleByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleByName(name));
    }

    @GetMapping("/ByAcademicYear")
    public ResponseEntity<?> getArticlesByAcademicYear(@RequestParam(name = "academicYear") int academicYear,
            @RequestParam(name = "faculty") int facultyId) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticlesByAcademicYear(academicYear,facultyId));
    }
}
