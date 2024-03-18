package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kmd.backend.magazine.dtos.CommentRequestDto;
import kmd.backend.magazine.dtos.CommentResponseDto;
import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.models.Comment;
import kmd.backend.magazine.repos.ArticleRepo;
import kmd.backend.magazine.repos.CommentRepo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    public List<CommentResponseDto> getAllComment(int articleId) {
        Article article = articleService.getArticleRaw(articleId);
        List<Comment> commentList = commentRepo.findByArticle(article);
        List<CommentResponseDto> cmtDto = new ArrayList<>();
        for (Comment cmt : commentList) {
            CommentResponseDto cmtRespDto = new CommentResponseDto();
            cmtRespDto.setId(cmt.getId());
            cmtRespDto.setArticle(articleService.getArticle(cmt.getArticle().getId()));
            cmtRespDto.setCreatedAt(cmt.getCreatedAt());
            cmtRespDto.setComment(cmt.getComment());
            cmtRespDto.setUser(userService.getUser(cmt.getUser().getId()));
            cmtDto.add(cmtRespDto);
        }
        return cmtDto;
    }

    public Comment addComment(CommentRequestDto cmtResqDto) {
        Article article = articleService.getArticleRaw(cmtResqDto.getArticleId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        if (article == null) {
            throw new EntityNotFoundException("Article");
        }
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setComment(cmtResqDto.getComment());
        comment.setUser(userService.getUserRaw(cmtResqDto.getUserId()));
        comment.setCreatedAt(LocalDateTime.now().format(formatter));
        return commentRepo.save(comment);
    }

    public Comment updateComment(CommentRequestDto cmtResqDto, int id) {
        Comment exitingCmt = commentRepo.findById(id).get();
        if (exitingCmt == null) {
            throw new EntityNotFoundException("Comment");
        }
        exitingCmt.setComment(cmtResqDto.getComment());
        return commentRepo.save(exitingCmt);

    }

    public void deleteComment(int id) {
        Comment exitingCmt = commentRepo.findById(id).get();
        if (exitingCmt == null) {
            throw new EntityNotFoundException("Comment");
        }
        commentRepo.delete(exitingCmt);
    }

}
