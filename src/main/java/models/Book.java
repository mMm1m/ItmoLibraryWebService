package models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonAutoDetect
public class Book {
    private List<Author> authors = null;
    private String bookName = null;
    private Long year = null;
    private String isbn = null;
    private Long id = null;
    public Book()
    {}
    public Book(List<Author> authors, String bookName, Long year, String isbn, Long id)
    {
        this.authors = authors;
        this.bookName = bookName;
        this.year = year;
        this.isbn = isbn;
        this.id = id;
    }
}
