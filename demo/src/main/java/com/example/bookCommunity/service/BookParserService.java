package com.example.bookCommunity.service;

import com.example.bookCommunity.exception.*;
import com.example.bookCommunity.model.Author;
import com.example.bookCommunity.model.Book;
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
