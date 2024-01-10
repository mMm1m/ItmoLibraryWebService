package com.example.demo1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonAutoDetect
@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    public long book_id;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "authors")
    public List<Author> authors = null;
    @Column(name = "BookName")
    public String bookName = null;
    @Column(name = "Year")
    public Long year = null;
    @Column(name = "Isbn")
    public String isbn = null;
    @Column(name = "Link")
    private String link = null;
    @Column(name = "Shop")
    private String shop = null;
    @Column(name = "Owner_id")
    private String owner_id = null;
    
    public Book(){}
    
    public Book(List<Author> authors, String bookName, Long year, String isbn, Long id)
    {
        this.authors = authors;
        this.bookName = bookName;
        this.year = year;
        this.isbn = isbn;
        this.book_id = id;
    }
    
    public Book(List<Author> authors, String bookName, Long year, String isbn, Long id, String link, String shop , String owner_id)
    {
    	this(authors , bookName , year , isbn , id);
    	this.link = link;
		this.shop = shop;
		this.owner_id = owner_id;
    }
    
    public List<Author> getAuthors()
    {
    	return this.authors;
    }
}