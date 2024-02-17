package kmd.backend.magazine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kmd.backend.magazine.services.CommentService;
import kmd.backend.magazine.models.Comment;
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{articelId}")
    public List<Comment> getCommentsByArticleId(@PathVariable int articleId) {
        return commentService.getComment(articleId);
    }

    @PostMapping()
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}
