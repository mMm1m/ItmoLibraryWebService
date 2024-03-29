package com.example.demo1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;


@Transactional
@Service
public class BookService implements IBookService {
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		return Lists.newArrayList(bookRepository.findAll());
	}
	
	/*@Override
	@Transactional(readOnly = true)
	public List<Book> getByName(String name) {
		return Lists.newArrayList(bookRepository.findByName(name));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> getByAuthor(String author) {
		return Lists.newArrayList(bookRepository.findByAuthor(author));
	}*/

	@Override
	@Transactional(readOnly = true)
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	@Override
	public boolean existsByIsbn(String isbn)
	{
		return bookRepository.existsByIsbn(isbn);
	}

	@Override
	public boolean existsByBookName(String name) {
		return bookRepository.existsByBookName(name);
	}
	
}