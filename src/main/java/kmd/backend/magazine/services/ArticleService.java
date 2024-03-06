package kmd.backend.magazine.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kmd.backend.magazine.dtos.ArticleDto;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.repos.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articlesRepo;
    @Autowired
    CommonService commonService;

    public Article getArticleRaw(int articelId) {

        return articlesRepo.findById(articelId).orElseThrow(() -> new EntityNotFoundException("Article not found!"));

    }

    public ArticleDto getArticle(int articleId) {
        Article article = new Article();
        try {
            article = getArticleRaw(articleId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Article not found!");
        }
        String fileDownloadURL = commonService.fileDownloadURL("api/v1/article/file", article.getFileData(),
                article.getFileName(), article.getId());
        String coverPhotoDownloadURL = commonService.fileDownloadURL("api/v1/article/coverPhoto",
                article.getCoverPhotoData(),
                article.getCoverPhotoName(), article.getId());
        return new ArticleDto(article.getId(), article.getTitle(), fileDownloadURL, coverPhotoDownloadURL,
                article.isApproveStatus(), article.isDeleteStatus(), article.getCreatedDate(),
                article.getUpdatedDate(), article.getAcademicYear(), article.getUser());
    }

    public List<ArticleDto> getAllArticles() {
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articlesRepo.findAll()) {
            String fileDownloadURL = commonService.fileDownloadURL("api/v1/article/file", article.getFileData(),
                    article.getFileName(), article.getId());
            String coverPhotoDownloadURL = commonService.fileDownloadURL("api/v1/article/coverPhoto",
                    article.getCoverPhotoData(),
                    article.getCoverPhotoName(), article.getId());
            articleDtos
                    .add(new ArticleDto(article.getId(), article.getTitle(), fileDownloadURL, coverPhotoDownloadURL,
                            article.isApproveStatus(), article.isDeleteStatus(), article.getCreatedDate(),
                            article.getUpdatedDate(), article.getAcademicYear(), article.getUser()));
        }
        return articleDtos;
    }

    public Article saveArticle(MultipartFile file, MultipartFile coverPhoto, Article article) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String coverPhotoName = StringUtils.cleanPath(coverPhoto.getOriginalFilename());
        try {
            if (!file.isEmpty()) {
                if (fileName.contains("..")) {
                    throw new Exception("Filename contains invalid path sequence "
                            + fileName);
                }
                article.setFileData(file.getBytes());
                article.setFileName(fileName);
                article.setFileType(file.getContentType());
            }
            if (!coverPhoto.isEmpty()) {
                if (coverPhotoName.contains("..")) {
                    throw new Exception("Cover Photo Name contains invalid path sequence "
                            + coverPhotoName);
                }
                article.setCoverPhotoData(file.getBytes());
                article.setCoverPhotoName(coverPhotoName);
                article.setCoverPhotoType(coverPhoto.getContentType());
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            article.setCreatedDate(LocalDate.now().format(formatter));
            article.setUpdatedDate(null);

            return articlesRepo.save(article);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Article updateArticle(MultipartFile file, MultipartFile coverPhoto, Article article) throws Exception {

        Article existingArticle = getArticleRaw(article.getId());

        if (existingArticle != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String coverPhotoName = StringUtils.cleanPath(coverPhoto.getOriginalFilename());
            try {
                if (!file.isEmpty()) {
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }
                    existingArticle.setFileData(file.getBytes());
                    existingArticle.setFileName(fileName);
                    existingArticle.setFileType(file.getContentType());
                }
                if (!coverPhoto.isEmpty()) {
                    if (coverPhotoName.contains("..")) {
                        throw new Exception("Cover Photo Name contains invalid path sequence "
                                + coverPhotoName);
                    }
                    existingArticle.setCoverPhotoData(file.getBytes());
                    existingArticle.setCoverPhotoName(coverPhotoName);
                    existingArticle.setCoverPhotoType(coverPhoto.getContentType());
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                existingArticle.setUpdatedDate(LocalDate.now().format(formatter));
                existingArticle.setTitle(article.getTitle());

                return articlesRepo.save(existingArticle);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new EntityNotFoundException("Article not found!");
        }

    }

    public void setApproveArticle(int articleId,boolean status) throws Exception {
        try {
            Article existingArticle = articlesRepo.findById(articleId).get();
            existingArticle.setApproveStatus(status);
            articlesRepo.save(existingArticle);
        } catch (Exception e) {
            throw new EntityNotFoundException("Article not found!");
        }
    }

}
