package com.example.demo1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class AuthorService implements IAuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Author> getAllAuthors() {
		return Lists.newArrayList(authorRepository.findAll());
	}

	@Override
	public Author getBySurname(String name, String surname) {
		
		return null;
	}

	@Override
	public Optional<Author> findById(Long id) {
		return authorRepository.findById(id);
	}

	@Override
	public Author save(Author author) {
		return authorRepository.save(author);
	}

}
