package com.example.demo1;


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
    List<Author> getAuthors(Document document);
    String getBookISBN(Document document) throws IncorrectBookISBN;
    Long getBookYear(Document document) throws IncorrectBookYear;
    Long getBookID(Document document) throws IncorrectBookID;
    boolean checkGenre(Document document);
    boolean checkIsCorrectShop(boolean criteria1, boolean criteria2);
    Book parseBookPage(String url) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID;
}
