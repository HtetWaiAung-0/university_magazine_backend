package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.exceptions.EntityNotFoundException;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.models.Comment;
import kmd.backend.magazine.repos.CommentRepo;
import java.util.List;
@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ArticleService articleService;

    public List<Comment> getComment(int articleId) {
        Article article = articleService.getArticle(articleId);
        return commentRepo.findByArticle(article);
    }

    public Comment addComment(Comment comment) {
        Article article = articleService.getArticle(comment.getArticle().getId());
        if(article == null) {
            throw new EntityNotFoundException("Article");
        }
        return commentRepo.save(comment);
    }
}
