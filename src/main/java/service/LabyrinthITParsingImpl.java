package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Author;
import models.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.StringWriter;
import java.util.*;

@Component
// parsing of thr page( realize book_parser and html_parsing)
public class LabyrinthITParsingImpl extends LabyrinthParsingHtml implements BookParser, HtmlParsing {
    @Autowired
    private Document document;
    private StringBuilder stringBuilder = new StringBuilder();
    private final Book book = new Book();
    private final List<Author> authors = new ArrayList<>();
    private final Set<String> idealSet =  new HashSet<>();

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
    public String getBookName() {
        String name = document.getElementById("product-title")
                .getElementsByTag("h1").text();
        this.book.setBookName(name);
        return  name;
    }

    @Override
    public List<Author> getAuthors() {
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
    public String getBookISBN() {
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
        } else throw new NullPointerException();
        this.stringBuilder = new StringBuilder();
        return string;
    }

    @Override
    public Long getBookYear() {
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
        } else throw new NumberFormatException();
        Long ans = Long.parseLong(string);
        stringBuilder = new StringBuilder();
        return ans;
    }

    @Override
    public Long getBookID() {
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
        } else throw new NumberFormatException();
        Long ans = Long.parseLong(string);
        stringBuilder = new StringBuilder();
        return ans;
    }

    // return json file of the book
    public String paramsResult() throws  NullPointerException {
        String ans = null;
        try
        {
            if(!checkIsCorrectShop(checkGenre(this.document), checkLabyrinthShopName(this.document)))
                throw new InvalidObjectException("Incorrect exception");
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, this.book);
            ans =  writer.toString();
        }
        catch (InvalidObjectException exception)
        {
            System.err.println("Incorrect shop information!!!");
            exception.printStackTrace();
        }
        catch(NullPointerException exception)
        {
            System.err.println("NullPointer exception!!!");
            exception.printStackTrace();
        }
        catch(IOException exception)
        {
            System.err.println("In-Out exception!!!");
            exception.printStackTrace();
        }
        return ans;
    }
}
