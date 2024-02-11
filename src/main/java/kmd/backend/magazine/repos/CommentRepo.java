package kmd.backend.magazine.repos;

import javax.xml.stream.events.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    
}
