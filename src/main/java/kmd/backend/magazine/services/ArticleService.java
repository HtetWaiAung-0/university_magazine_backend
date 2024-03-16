package kmd.backend.magazine.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import kmd.backend.magazine.dtos.ArticelRequestDto;
import kmd.backend.magazine.dtos.ArticelResponseDto;
import kmd.backend.magazine.dtos.UserResponseDto;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.repos.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articlesRepo;
    @Autowired
    CommonService commonService;
    @Autowired
    private UserService userService;
    @Autowired
    private AcademicYearService academicYearService;

    public Article getArticleRaw(int articelId) {

        return articlesRepo.findById(articelId).orElseThrow(() -> new EntityNotFoundException("Article not found!"));

    }

    public ArticelResponseDto getArticle(int articleId) {
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
        UserResponseDto userResponseDto = userService.getUser(article.getUser().getId());
        return new ArticelResponseDto(article.getId(), article.getTitle(), fileDownloadURL, coverPhotoDownloadURL,
                article.isApproveStatus(), article.isDeleteStatus(), article.getCreatedDate(),
                article.getUpdatedDate(), article.getAcademicYear(), userResponseDto);
    }

    public List<ArticelResponseDto> getAllArticles() {
        List<ArticelResponseDto> articelResponseDtos = new ArrayList<>();
        for (Article article : articlesRepo.findAll()) {
            String fileDownloadURL = commonService.fileDownloadURL("api/v1/article/file", article.getFileData(),
                    article.getFileName(), article.getId());
            String coverPhotoDownloadURL = commonService.fileDownloadURL("api/v1/article/coverPhoto",
                    article.getCoverPhotoData(),
                    article.getCoverPhotoName(), article.getId());

            UserResponseDto userResponseDto = userService.getUser(article.getUser().getId());
            articelResponseDtos
                    .add(new ArticelResponseDto(article.getId(), article.getTitle(), fileDownloadURL, coverPhotoDownloadURL,
                            article.isApproveStatus(), article.isDeleteStatus(), article.getCreatedDate(),
                            article.getUpdatedDate(), article.getAcademicYear(), userResponseDto));
        }
        return articelResponseDtos;
    }

    public Article saveArticle(ArticelRequestDto articelRequestDto) throws Exception {
        Article article = new Article();
        
        
        try {
            if (!articelRequestDto.getFile().isEmpty()) {
                @SuppressWarnings("null")
                String fileName = StringUtils.cleanPath(articelRequestDto.getFile().getOriginalFilename());
                if (fileName.contains("..")) {
                    throw new Exception("Filename contains invalid path sequence "
                            + fileName);
                }
                article.setFileData(articelRequestDto.getFile().getBytes());
                article.setFileName(fileName);
                article.setFileType(articelRequestDto.getFile().getContentType());
            }
            if (!articelRequestDto.getCoverPhoto().isEmpty()) {
                @SuppressWarnings("null")
                String coverPhotoName = StringUtils.cleanPath(articelRequestDto.getCoverPhoto().getOriginalFilename());
                if (coverPhotoName.contains("..")) {
                    throw new Exception("Cover Photo Name contains invalid path sequence "
                            + coverPhotoName);
                }
                article.setCoverPhotoData(articelRequestDto.getCoverPhoto().getBytes());
                article.setCoverPhotoName(coverPhotoName);
                article.setCoverPhotoType(articelRequestDto.getCoverPhoto().getContentType());
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            article.setCreatedDate(LocalDate.now().format(formatter));
            article.setUpdatedDate(null);

            article.setTitle(articelRequestDto.getTitle());
            article.setAcademicYear(academicYearService.getAcademicYear(articelRequestDto.getAcademicYear()));
            article.setUser(userService.getUserRaw(articelRequestDto.getUser()));


            return articlesRepo.save(article);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // public Article updateArticle(ArticelRequestDto articelRequestDto,int articelId) throws Exception {

    //     Article existingArticle = getArticleRaw(articelId);

    //     if (existingArticle != null) {
            
            
    //         try {
    //             if (!articelRequestDto.getFile().isEmpty()) {
    //                 String fileName = StringUtils.cleanPath(articelRequestDto.getFile().getOriginalFilename());
    //                 if (fileName.contains("..")) {
    //                     throw new Exception("Filename contains invalid path sequence "
    //                             + fileName);
    //                 }
    //                 existingArticle.setFileData(articelRequestDto.getFile().getBytes());
    //                 existingArticle.setFileName(fileName);
    //                 existingArticle.setFileType(articelRequestDto.getFile().getContentType());
    //             }
    //             if (!articelRequestDto.getCoverPhoto().isEmpty()) {
    //                 String coverPhotoName = StringUtils.cleanPath(articelRequestDto.getCoverPhoto().getOriginalFilename());
    //                 if (coverPhotoName.contains("..")) {
    //                     throw new Exception("Cover Photo Name contains invalid path sequence "
    //                             + coverPhotoName);
    //                 }
    //                 existingArticle.setCoverPhotoData(articelRequestDto.getCoverPhoto().getBytes());
    //                 existingArticle.setCoverPhotoName(coverPhotoName);
    //                 existingArticle.setCoverPhotoType(articelRequestDto.getCoverPhoto().getContentType());
    //             }
    //             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    //             existingArticle.setUpdatedDate(LocalDate.now().format(formatter));


    //             existingArticle.setTitle(articelRequestDto.getTitle());
    //             existingArticle.setAcademicYear(academicYearService.getAcademicYear(articelRequestDto.getAcademicYear()));
    //             existingArticle.setUser(userService.getUserRaw(articelRequestDto.getUser()));

    //             return articlesRepo.save(existingArticle);
    //         } catch (Exception e) {
    //             throw new Exception(e.getMessage());
    //         }
    //     } else {
    //         throw new EntityNotFoundException("Article not found!");
    //     }

    //}

    public Article updateArticle(ArticelRequestDto articelRequestDto, int articelId) throws Exception {
        
        Article existingArticle = getArticleRaw(articelId);
    
        if (existingArticle != null) {
            try {
                if (articelRequestDto.getFile() != null && !articelRequestDto.getFile().isEmpty()) {
                    String fileName = StringUtils.cleanPath(articelRequestDto.getFile().getOriginalFilename());
                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence " + fileName);
                    }
                    existingArticle.setFileData(articelRequestDto.getFile().getBytes());
                    existingArticle.setFileName(fileName);
                    existingArticle.setFileType(articelRequestDto.getFile().getContentType());
                }
                if (articelRequestDto.getCoverPhoto() != null && !articelRequestDto.getCoverPhoto().isEmpty()) {
                    String coverPhotoName = StringUtils.cleanPath(articelRequestDto.getCoverPhoto().getOriginalFilename());
                    if (coverPhotoName.contains("..")) {
                        throw new Exception("Cover Photo Name contains invalid path sequence " + coverPhotoName);
                    }
                    existingArticle.setCoverPhotoData(articelRequestDto.getCoverPhoto().getBytes());
                    existingArticle.setCoverPhotoName(coverPhotoName);
                    existingArticle.setCoverPhotoType(articelRequestDto.getCoverPhoto().getContentType());
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                existingArticle.setUpdatedDate(LocalDate.now().format(formatter));
                existingArticle.setId(articelId);
                existingArticle.setTitle(articelRequestDto.getTitle());
                existingArticle.setAcademicYear(academicYearService.getAcademicYear(articelRequestDto.getAcademicYear()));
                existingArticle.setUser(userService.getUserRaw(articelRequestDto.getUser()));
    
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
