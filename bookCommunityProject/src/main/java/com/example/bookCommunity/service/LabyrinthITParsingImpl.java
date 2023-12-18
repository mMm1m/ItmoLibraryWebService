package com.example.bookCommunity.service;

import com.example.bookCommunity.exception.*;
import com.example.bookCommunity.model.Author;
import com.example.bookCommunity.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.StringWriter;
import java.util.*;
import java.lang.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
// parsing of thr page( realize book_parser and html_parsing)
public class LabyrinthITParsingImpl extends LabyrinthParsingHtml implements BookParserService {
    // заполнить поля в соответствующих методах
    private Book book = null;
    private Set<String> idealSet =  fillSet();
    private Map<Author, Set<Book>> map = new HashMap<>(); 

    @Override
    public boolean checkLabyrinthShopName(Document document) {
        Elements elementClass = document.getElementsByClass("b-header-b-logo-e-logo-wrap");
        String shopName_ = "Лабиринт - самый большой книжный интернет магазин";
        for(var a : elementClass)
            if(a.attr("title").equals(shopName_)) return true;
        return false;
    }

    public Set<String> fillSet()
    {
        this.idealSet = new HashSet<>();
        Logger logger = LoggerFactory.getLogger(LabyrinthITParsingImpl.class);
        try {
            idealSet.add("Информационные технологии");
            // all others
            idealSet.add("Базы данных");
            idealSet.add("Графика. Дизайн. Проектирование");
            idealSet.add("Железо ПК");
            idealSet.add("Интернет");
            idealSet.add("Информатика");
            idealSet.add("Машинное обучение. Анализ данных");
            idealSet.add("Менеджмент в IT");
            idealSet.add("Операционные системы и утилиты для ПК");
            idealSet.add("Программирование");
            idealSet.add("Программы и утилиты для цифровых устройств");
            idealSet.add("Руководства по пользованию программами");
            idealSet.add("Сети и коммуникации");
            idealSet.add("Электронная бухгалтерия");
        }
        catch (NullPointerException exception)
        {
            logger.error("Null pointer exception!!!", exception);
        }
        return this.idealSet;
    }

    @Override
    public boolean checkGenre(Document document) {
        Element genre = document.getElementById("thermometer-books");
        Elements select = genre.select("span");
        fillSet();
        int initSize = idealSet.size();
        for(var a : select)
        {
            var tmp = a.getElementsByAttribute("itemprop").text();
            for(int i = 0; i < tmp.length(); ++i)
            {
                if(idealSet.contains(tmp.substring(0, i))){
                    idealSet.remove(tmp.substring(0, i));
                }
            }
        }
        return (idealSet.size() != initSize);
    }

    @Override
    public boolean checkIsCorrectShop(boolean criteria1, boolean criteria2) {
        return (criteria1 && criteria2);
    }

    public Document getDocument(String url) throws IOException {
        // String url = "https://www.labirint.ru/books/512969/";
        Document doc = null;
        Logger logger = LoggerFactory.getLogger(LabyrinthITParsingImpl.class);
        try
        {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .referrer("https://google.com")
                    .get();
            if(!checkIsCorrectShop(checkGenre(doc), checkLabyrinthShopName(doc)))
                throw new InvalidObjectException("Incorrect exception");
        }
        catch (IOException exception)
        {
            logger.error("Exception in input-output!!!", exception);
        }
        return doc;
    }

    @Override
    public String parseBookPage(String url) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID {
        String ans = null;
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        Document doc = getDocument(url);
        Logger logger = LoggerFactory.getLogger(LabyrinthITParsingImpl.class);
        try
        {
            this.book = new Book(getAuthors(doc), getBookName(doc), getBookYear(doc), getBookISBN(doc), getBookID(doc));
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(writer, this.book);
            ans = writer.toString();
            System.out.println(ans);
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

    public Map<Author , Set<Book>> fillMap(Set<Author> authors)
    {
        for(var a : authors)
        {
            map.putIfAbsent(a,  new HashSet<>());
            if(map.get(a).isEmpty()) {
                map.put(a, new HashSet<>(List.of(this.book)));
                continue;
            }
            Set<Book> books = map.get(a);
            books.add(this.book);
            map.put(a, books);
        }
        return this.map;
    }

    @Override
    //@Scheduled(fixedDelay = 5000)
    public String parseAuthorPage(String url) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID {
        String ans = null;
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        Document doc = getDocument(url);
        Logger logger = LoggerFactory.getLogger(LabyrinthITParsingImpl.class);
        try
        {
        this.book = new Book(getAuthors(doc), getBookName(doc), getBookYear(doc), getBookISBN(doc), getBookID(doc));
        Set<Author> authors = this.book.getAuthors();
        this.map = fillMap(authors);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(writer , map);
        ans =  writer.toString();
        System.out.println(ans);
            System.out.println();
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

    @Override
    public String getBookName(Document document) {
        String name = document.getElementById("product-title")
                .getElementsByTag("h1").text();
        int index = name.indexOf(':');
        name = name.substring(index+1, name.length()).trim();
        return  name;
    }

    @Override
    public Set<Author> getAuthors(Document document) {
        var authorTmp = document.getElementsByClass("authors");
        Set<Author> authorList = new HashSet<>();
        int curr_start = 0, count = 0;
        for (var a : authorTmp) {
            if(!a.text().trim().startsWith("Автор")) continue;
            var k = a.getElementsByAttribute("data-event-content").text().trim();
            Author author = null;
            for(int i = 0; i < k.length(); ++i)
            {
                if((k.charAt(i) == ' '))
                {
                    if(count % 2 == 0)
                    {
                        author = new Author();
                        author.setAuthorSurname(k.substring(curr_start,i));
                        curr_start = i;
                    }
                    else
                    {
                        author.setAuthorName(k.substring(curr_start,i));
                        curr_start = i;
                        authorList.add(author);
                        author = new Author();
                    }
                    ++count;
                }
            }
            if(author != null)
            {
                author.setAuthorName(k.substring(curr_start,k.length()));
                authorList.add(author);
            }
        }
        System.out.println(authorList);
        return authorList;
    }

    @Override
    public String getBookISBN(Document document) throws IncorrectBookISBN {
        String bookISBN = document.getElementsByClass("isbn").text();
        int i = bookISBN.length()-1;
        StringBuilder builder = new StringBuilder();
        while( i >= 0)
        {
            if(Character.isDigit(bookISBN.charAt(i))) {
                while(bookISBN.charAt(i) != ' ')
                {
                    builder.append(bookISBN.charAt(i));
                    --i;
                }
                return builder.reverse().toString();
            }
            --i;
        }
        if (builder.isEmpty()) throw new IncorrectBookISBN();
        return null;
    }

    public StringBuilder getNumbers(String year, StringBuilder builder)
    {
        for (int i = year.length() - 1; i >= 0; --i) {
            while (Character.isDigit(year.charAt(i))) {
                builder.append(year.charAt(i));
                --i;
            }
            if(!builder.isEmpty()) break;
        }
        return builder;
    }

    @Override
    public Long getBookYear(Document document) throws IncorrectBookYear {
        String year = document.getElementsByClass("publisher").text();
        StringBuilder builder = new StringBuilder();
        StringBuilder string = getNumbers(year , builder);
        if (!string.isEmpty())
            return Long.parseLong(string.reverse().toString());
        else throw new IncorrectBookYear();
    }

    @Override
    public Long getBookID(Document document) throws IncorrectBookID {
        String bookID = document.getElementsByClass("articul").text();
        StringBuilder builder = new StringBuilder();
        StringBuilder string = getNumbers(bookID , builder);
        if (!string.isEmpty())
            return Long.parseLong(string.reverse().toString());
        else throw new IncorrectBookID();
    }
}
