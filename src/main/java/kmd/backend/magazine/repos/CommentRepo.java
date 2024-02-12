package kmd.backend.magazine.repos;



import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    
}
