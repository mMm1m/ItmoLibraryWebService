package com.example.demo1;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
	List<Token> findAllValidTokenByUserId(Integer id);
	Optional<Token> findByToken(String token);
}