package kmd.backend.magazine.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.repos.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articlesRepo;
    @Autowired
    CommonService commonService;

    public Article getArticle(int articleId) {
        return articlesRepo.findById(articleId).orElse(null);
    }

    public Article uploadArticle(Article article, MultipartFile coverPhoto, MultipartFile file) throws IOException {
        if (article == null) {
            throw new IllegalArgumentException("Article is null");
        }

        article.setCoverPhoto(coverPhoto.getOriginalFilename());
        article.setFileName(file.getOriginalFilename());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (article.getCreatedDate() == null) {
            article.setCreatedDate(LocalDate.now().format(formatter));
            article.setUpdatedDate(null);
        }

        Article savedArticle = articlesRepo.save(article);
        commonService.fileLocalUpload(coverPhoto, "coverPhoto", savedArticle.getId());
        commonService.fileLocalUpload(file, "document", savedArticle.getId());
        return savedArticle;
    }

    public List<Article> getAllArticles() {
        return articlesRepo.findAll();
    }

    public String deleteArticle(int articleId) {
        return "";
    }

}
