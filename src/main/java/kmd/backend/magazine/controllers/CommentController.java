package kmd.backend.magazine.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kmd.backend.magazine.services.CommentService;
import kmd.backend.magazine.dtos.CommentRequestDto;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getCommentsByArticleId(@PathVariable int articleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComment(articleId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@ModelAttribute CommentRequestDto comment) {
        commentService.addComment(comment);
        return ResponseEntity.ok().body("Comment added");
    }

    @PostMapping("/update/{cmtId}")
    public ResponseEntity<?> updateComment(@ModelAttribute CommentRequestDto comment, @PathVariable int cmtId) {
        commentService.updateComment(comment, cmtId);
        return ResponseEntity.ok().body("Comment Updated");
    }

}
