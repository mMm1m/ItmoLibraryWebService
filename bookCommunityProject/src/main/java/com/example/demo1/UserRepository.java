package com.example.demo1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByName(String name);
	Optional<UserEntity> findByLogin(String login);
	Optional<UserEntity> findByPassword(String password);
    Boolean existsByName(String username);
    Boolean existsByLogin(String login);
    Boolean existsByPassword(String password);
    
    
}
