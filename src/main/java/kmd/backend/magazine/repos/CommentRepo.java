package kmd.backend.magazine.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.Comment;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticles(int articleId);
}
