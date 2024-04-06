package kmd.backend.magazine.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kmd.backend.magazine.models.Article;


public interface ArticleRepo extends JpaRepository<Article, Integer> {
    Article findByIdAndDeleteStatus(int articleId, boolean deleteStatus);

    List<Article> findByDeleteStatus(boolean deleteStatus);
    List<Article> findByDeleteStatusAndTitleContainingIgnoreCase(boolean deleteStatus, String title);

    @Query(value = "select a.* from article a\r\n" + //
                "inner join users u on a.user_id = u.id\r\n" + //
                "inner join faculty f on u.faculty_id = f.id\r\n" + //
                "where a.delete_status = false and f.delete_status = false and f.id = :facultyId", nativeQuery = true)
    List<Article> findArticleByFacultyId(@Param("facultyId") int facultyId);

    

    

    List<Article> findArticleByApproveStatus(Article.ApproveStatus status);

    

}
