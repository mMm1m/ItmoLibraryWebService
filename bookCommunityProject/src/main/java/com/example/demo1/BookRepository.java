package com.example.demo1;


import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// методы для использования базы данных
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	/*List<Book> findByName(String name);
	List<Book> findByAuthor(String name);*/
}
