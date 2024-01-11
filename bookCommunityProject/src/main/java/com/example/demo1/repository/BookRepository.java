package com.example.demo1;


import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	public boolean existsByIsbn(String isbn);
	public boolean existsByBookName(String name);
	//List<Book> findByName(String name);
	//List<Book> findByAuthor(String name);
	//Optional<Book> findById(Long id);
}
