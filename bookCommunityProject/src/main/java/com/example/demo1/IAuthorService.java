package com.example.demo1;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
	List<Author> getAllAuthors();
	Author getBySurname(String name , String surname);
	Optional<Author> findById(Long id);
	Author save(Author book);
}
