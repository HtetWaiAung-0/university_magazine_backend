package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kmd.backend.magazine.models.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
""")
    List<Token> findAllTokensByUser(Integer userId);

    List<Token> findByUserId(int userId);

    Optional<Token> findByToken(String token);
}