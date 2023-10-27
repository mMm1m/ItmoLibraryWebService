package service;

import exception.IncorrectBookID;
import exception.IncorrectBookISBN;
import exception.IncorrectBookYear;
import models.Author;
import models.Book;

import java.util.List;
// common parser for all library_type books

public interface BookParser {
    String getBookName(Book book);
    List<Author> getAuthors(Book book);
    String getBookISBN(Book book) throws IncorrectBookISBN;
    Long getBookYear(Book book) throws IncorrectBookYear;
    Long getBookID(Book book) throws IncorrectBookID;

}
