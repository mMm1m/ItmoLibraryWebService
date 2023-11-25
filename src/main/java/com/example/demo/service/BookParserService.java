package com.example.demo.service;

import com.example.demo.exception.IncorrectBookID;
import com.example.demo.exception.IncorrectBookISBN;
import com.example.demo.exception.IncorrectBookYear;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
// common parser for all library_type books

@Service
@Component
public interface BookParserService {
    String getBookName(Document document);
    Set<Author> getAuthors(Document document);
    String getBookISBN(Document document) throws IncorrectBookISBN;
    Long getBookYear(Document document) throws IncorrectBookYear;
    Long getBookID(Document document) throws IncorrectBookID;
    boolean checkGenre(Document document);
    boolean checkIsCorrectShop(boolean criteria1, boolean criteria2);
    String parseBookPage(String url) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID;
    String parseAuthorPage(String url) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID;
}
