package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.controller.RequestController;
import com.example.demo.exception.IncorrectBookID;
import com.example.demo.exception.IncorrectBookISBN;
import com.example.demo.exception.IncorrectBookYear;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;

import java.io.InvalidObjectException;
import java.io.StringWriter;
import java.util.*;

@Service
// parsing of thr page( realize book_parser and html_parsing)
public class LabyrinthITParsingImpl extends LabyrinthParsingHtml implements BookParser, HtmlParsing {
    private StringBuilder stringBuilder = new StringBuilder();
    private Book book;
    private final Set<String> idealSet =  new HashSet<>();
    private final RequestController requestController = new RequestController();
    private Document document = requestController.getDocument();

    @Override
    public boolean checkLabyrinthShopName(Document document) {
        Elements elementClass = document.getElementsByClass("b-header-b-logo-e-logo-wrap");
        String shopName_ = "Лабиринт - самый большой книжный интернет магазин";
        for(var a : elementClass)
            if(a.attr("title").equals(shopName_)) return true;
        return false;
    }

    @Override
    public boolean checkGenre(Document document) {
        Element genre = document.getElementById("thermometer-books");
        Elements select = genre.select("span");
        // main genre
        idealSet.add("Информационные технологии");
        // all others
        idealSet.add("Базы данных"); idealSet.add("Графика. Дизайн. Проектирование");
        idealSet.add("Железо ПК"); idealSet.add("Интернет");
        idealSet.add("Информатика"); idealSet.add("Машинное обучение. Анализ данных");
        idealSet.add("Менеджмент в IT"); idealSet.add("Операционные системы и утилиты для ПК");
        idealSet.add("Программирование"); idealSet.add("Программы и утилиты для цифровых устройств");
        idealSet.add("Руководства по пользованию программами"); idealSet.add("Сети и коммуникации");
        idealSet.add("Электронная бухгалтерия");
        int initSize = idealSet.size();
        for(var a : select)
        {
            var tmp = a.getElementsByAttribute("itemprop").text();
            if(idealSet.contains(tmp))
                idealSet.remove(tmp);
        }
        return (idealSet.size() != initSize);
    }

    @Override
    public boolean checkIsCorrectShop(boolean criteria1, boolean criteria2) {
        return (criteria1 && criteria2);
    }

    @Override
    public String getBookName(Book book) {
        String name = document.getElementById("product-title")
                .getElementsByTag("h1").text();
        book.setBookName(name);
        return  name;
    }

    @Override
    public List<Author> getAuthors(Book book) {
        var authorTmp = document.getElementsByClass("authors");
        List<String> authors = new LinkedList<>();
        List<Author> authorList = new ArrayList<>();
        for (var a : authorTmp) {
            authors.add(a.getElementsByAttribute("data-event-content").text());
        }
        int space = 0;
        for(var a : authors)
        {
            for(int i = 0; i < a.length(); ++i)
                if(a.charAt(i) == ' ') {space = i; break;}
            Author author = new Author();
            author.setAuthorName(a.substring(0,space));
            author.setAuthorSurname(a.substring(space+1, a.length()));
            this.book.getAuthors().add(author);
        }
        return authorList;
    }

    @Override
    public String getBookISBN(Book book) throws IncorrectBookISBN {
        String bookISBN = document.getElementsByClass("isbn").text();

        for (int i = bookISBN.length() - 1; i >= 0; --i) {
            char curr = bookISBN.charAt(i);
            if (curr == ' ') break;
            else this.stringBuilder.append(curr);
        }
        String string;
        if (!stringBuilder.isEmpty()) {
            string = stringBuilder.reverse().toString();
            this.book.setIsbn(string);
        } else throw new IncorrectBookISBN();
        this.stringBuilder = new StringBuilder();
        return string;
    }

    @Override
    public Long getBookYear(Book book) throws IncorrectBookYear {
        String year = document.getElementsByClass("publisher").text();
        for (int i = year.length() - 1; i >= 0; --i) {
            char curr = year.charAt(i);
            while (Character.isDigit(curr)) {
                stringBuilder.append(curr);
                --i;
            }
            if(!stringBuilder.isEmpty()) break;
        }
        String string;
        if (!stringBuilder.isEmpty()) {
            string = stringBuilder.reverse().toString();
            this.book.setYear(Long.parseLong(string));
        } else throw new IncorrectBookYear();
        Long ans = Long.parseLong(string);
        stringBuilder = new StringBuilder();
        return ans;
    }

    @Override
    public Long getBookID(Book book) throws IncorrectBookID {
        String bookID = document.getElementsByClass("articul").text();
        int idx = bookID.length()-1;
        while(Character.isDigit(bookID.charAt(idx)))
        {
            stringBuilder.append(bookID.charAt(idx--));
        }
        String string;
        if (!stringBuilder.isEmpty()) {
            string = stringBuilder.reverse().toString();
            this.book.setId(Long.parseLong(bookID));
        } else throw new IncorrectBookID();
        Long ans = Long.parseLong(string);
        stringBuilder = new StringBuilder();
        return ans;
    }

    // return json file of the book
    public String paramsResult()  {
        String ans = null;
        Logger logger = LoggerFactory.getLogger(LabyrinthITParsingImpl.class);
        try
        {
            if(!checkIsCorrectShop(checkGenre(this.document), checkLabyrinthShopName(this.document)))
                throw new InvalidObjectException("Incorrect exception");
            ObjectMapper objectMapper = new ObjectMapper();
            this.book = new Book(getAuthors(this.book), getBookName(this.book), getBookYear(this.book), getBookISBN(this.book), getBookID(this.book));
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, this.book);
            ans =  writer.toString();
        }
        catch(InvalidObjectException exception)
        {
            logger.error(exception.getMessage(), exception);
        }
        catch (IncorrectBookID exception)
        {
            logger.error("Incorrect shop information!!!", exception);
        }
        catch(IncorrectBookISBN exception)
        {
            logger.error("NullPointer exception!!!", exception);
        }
        catch(IncorrectBookYear exception)
        {
            logger.error("In-Out exception!!!", exception);
        }
        catch(Exception exception)
        {
            logger.error("", exception);
        }
        return ans;
    }
}
