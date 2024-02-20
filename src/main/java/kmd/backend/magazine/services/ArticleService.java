package kmd.backend.magazine.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.repos.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articlesRepo;

    public Article getArticle(int articleId) {
        return articlesRepo.findById(articleId).orElse(null);
    }
    
    public Article uploadArticle(Article article) {
        if(article == null){
            throw new IllegalArgumentException("Article is null");
        }
        return articlesRepo.save(article);
    }

    public List<Article> getAllArticles() {
        return articlesRepo.findAll();
    }

    public String deleteArticle(int articleId){
        return "";
    }


    public void fileLocalUpload(MultipartFile file) throws IOException {

        
        String uploadDir = "assets/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        InputStream inputStream = file.getInputStream();
        System.out.println(inputStream);
        if (!file.isEmpty()) {
            // try (inputStream) {
                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            // } catch (IOException e) {
            //     throw new IOException("Error uploading file: " + e.getMessage(), e);
            // }
        }
    }
    
}

