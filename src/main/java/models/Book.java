package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Book {
    private List<Author> authors = null;
    private String bookName = null;
    private Long year = null;
    private String isbn = null;
    private Long id = null;
}
