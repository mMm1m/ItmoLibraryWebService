package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// методы для использования базы данных
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
