package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class Demo1Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Demo1Application.class, args);
        RequestController controller = new RequestController();
        String curr = controller.getHTMLFromHTTPClient();
        String it_book = "https://www.labirint.ru/books/512969/";
        String eng_book = "https://www.labirint.ru/books/263504/";
        var server = new LibraryServer();
        var b = server.parseHTML(it_book);
        for(var a : b.getFirst())
        {
            System.out.println(a);
        }
        System.out.println();
        for(var a : b.getSecond())
        {
            System.out.println(a);
        }
    }
}
