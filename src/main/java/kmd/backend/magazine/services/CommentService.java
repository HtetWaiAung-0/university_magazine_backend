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

    public List<Comment> getComment(int articelId) {
        return commentRepo.findByArticles(articelId);
    }

    public Comment addComment(Comment comment) {
        Article article = articleService.getArticle(comment.getArticles().getId());
        if(article == null) {
            throw new EntityNotFoundException("Aticle");
        }
        return commentRepo.save(comment);
    }
}
