package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonAutoDetect
@Entity
public class Book {
    @Id
    private long key;
    @Column(name = "Authors")
    @OneToMany
    private Set<Author> authors = null;
    @Column(name = "BookName")
    private String bookName = null;
    @Column(name = "Year")
    private Long year = null;
    @Column(name = "isbn")
    private String isbn = null;
    public Book()
    {}
    public Book(Set<Author> authors, String bookName, Long year, String isbn, Long id)
    {
        this.authors = authors;
        this.bookName = bookName;
        this.year = year;
        this.isbn = isbn;
        this.key = id;
    }
}
