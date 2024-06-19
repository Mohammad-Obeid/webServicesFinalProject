package com.example.webservicesfinalproject.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.webservicesfinalproject.token.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
             SELECT t FROM Token t inner join User u\s
             on t.user.userID = u.userID\s 
            where u.userID = :id and (t.expired = false or t.revoked = false)\s
            """)

    List<Token> findAllValidTokenByUser(int id);



    Optional<Token> findByToken(String token);
}
