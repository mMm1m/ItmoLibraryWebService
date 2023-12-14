package com.example.bookCommunity.controller;

import com.example.bookCommunity.exception.IncorrectBookID;
import com.example.bookCommunity.exception.IncorrectBookISBN;
import com.example.bookCommunity.exception.IncorrectBookYear;
import com.example.bookCommunity.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
//import org.springframework.web.reactive.function.client.WebClient;


@RestController
public class RequestController {
    BookParserService scraperService;

    // метод проверяющий что ссылка на книгу валидна
    @GetMapping("/books/{id}")
    public String parsing(HttpServletRequest request) throws IncorrectBookYear, IncorrectBookID, IOException, IncorrectBookISBN {
       // database operations
         return scraperService.parseBookPage(request.getRequestURL().toString());
    }
    // добавить метод, проверяющий что isbn сслыки и isbn книги совпадают
}