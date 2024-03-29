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
    public Long book_id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
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
    
    public Book(){}
    
    public Book( String bookName, Long year, String isbn, Long id)
    {
        this.bookName = bookName;
        this.year = year;
        this.isbn = isbn;
        this.book_id = id;
    }
    
    public Book(String bookName, Long year, String isbn, Long id, String link, String shop)
    {
    	this(bookName , year , isbn , id);
    	this.link = link;
		this.shop = shop;
    }
}