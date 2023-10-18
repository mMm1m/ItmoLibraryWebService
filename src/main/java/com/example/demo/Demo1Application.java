package com.example.demo;

import controller.RequestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class Demo1Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Demo1Application.class, args);
        //String it_book = "https://www.labirint.ru/books/512969/";
        //String eng_book = "https://www.labirint.ru/books/263504/";
    }
}
