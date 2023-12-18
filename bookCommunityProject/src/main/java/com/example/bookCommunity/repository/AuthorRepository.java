package com.example.bookCommunity.repository;

import com.example.bookCommunity.model.Author;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// методы для использования базы данных
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
