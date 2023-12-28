package com.example.demo1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByName(String name);
	Optional<User> findByLogin(String login);
	Optional<User> findByPassword(String password);
    Boolean existsByName(String username);
    Boolean existsByPassword(String password);
    
    
}
