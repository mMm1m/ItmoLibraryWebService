package com.example.demo.service;

import com.example.demo.exception.IncorrectBookID;
import com.example.demo.exception.IncorrectBookISBN;
import com.example.demo.exception.IncorrectBookYear;
import com.example.demo.models.Author;
import com.example.demo.models.Book;

import java.util.List;
// common parser for all library_type books

public interface BookParser {
    String getBookName(Book book);
    List<Author> getAuthors(Book book);
    String getBookISBN(Book book) throws IncorrectBookISBN;
    Long getBookYear(Book book) throws IncorrectBookYear;
    Long getBookID(Book book) throws IncorrectBookID;

}
