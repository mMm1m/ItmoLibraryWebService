package com.example.demo;

import com.example.demo.exception.IncorrectBookID;
import com.example.demo.exception.IncorrectBookISBN;
import com.example.demo.exception.IncorrectBookYear;
import com.example.demo.model.Author;
import com.example.demo.service.LabyrinthITParsingImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@EnableScheduling
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Demo1Application {
    public static void main(String[] args) throws IOException, InterruptedException, IncorrectBookID, IncorrectBookISBN, IncorrectBookYear {
        SpringApplication.run(Demo1Application.class, args);
        LabyrinthITParsingImpl parsing = new LabyrinthITParsingImpl();
        String url1 = "https://www.labirint.ru/books/512969/";
        String url2 = "https://www.labirint.ru/books/633862/";
        String url3 = "https://www.labirint.ru/books/558936/";
        Document doc1 = Jsoup.connect("https://www.labirint.ru/books/512969/").get();
        Document doc2 = Jsoup.connect("https://www.labirint.ru/books/633862/").get();
        Document doc3 = Jsoup.connect("https://www.labirint.ru/books/558936/").get();
        parsing.parseAuthorPage(url1);
        parsing.parseAuthorPage(url2);
        parsing.parseAuthorPage(url3);
    }
}
