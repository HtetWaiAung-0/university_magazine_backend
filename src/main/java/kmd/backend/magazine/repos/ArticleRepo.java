package kmd.backend.magazine.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.Article;
import kmd.backend.magazine.models.Faculty;


public interface ArticleRepo extends JpaRepository<Article, Integer> {
    Article findByIdAndDeleteStatus(int articleId, boolean deleteStatus);

    List<Article> findByDeleteStatus(boolean deleteStatus);

     List<Article> findByFacultyandDeleteStatus(Faculty faculty,boolean deleteStatus);

}
