package com.example.demo1;

import java.util.List;

public interface IBookService {
	List<Book> getAllBooks();
	List<Book> getByName(String name);
	List<Book> getByAuthor(String author);
	Book findById(Long id);
	Book save(Book book);
	boolean existsByIsbn(String isbn);
	boolean existsByBookName(String name);
}
