package service;

import models.Author;
import java.util.List;
// common parser for all library_type books

public interface BookParser {
    String getBookName();
    List<Author> getAuthors();
    String getBookISBN();
    Long getBookYear();
    Long getBookID();

}
