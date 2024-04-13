package kmd.backend.magazine.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.models.Comment;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByArticle(Article article);
    Comment findById(int id);

    @Modifying
    @Query(value = "delete from comment where id=:id", nativeQuery = true)
    void deleteById(@Param("id") int id);
}
