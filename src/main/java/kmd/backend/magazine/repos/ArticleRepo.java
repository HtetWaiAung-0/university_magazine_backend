package kmd.backend.magazine.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kmd.backend.magazine.models.Article;


public interface ArticleRepo extends JpaRepository<Article, Integer> {
    Article findByIdAndDeleteStatus(int articleId, boolean deleteStatus);

    List<Article> findByDeleteStatus(boolean deleteStatus);

    List<Article> findByNameAndDeleteStatus(String articleName, boolean deleteStatus);

    List<Article> findByName(String articleName);

}
