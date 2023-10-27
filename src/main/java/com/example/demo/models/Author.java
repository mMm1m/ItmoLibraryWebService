package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Author {
    private String authorName = null;
    private String authorSurname = null;
    private List<Book> bookList = null;
}
