package com.example.demo.controller;

import com.example.demo.exception.IncorrectBookID;
import com.example.demo.exception.IncorrectBookISBN;
import com.example.demo.exception.IncorrectBookYear;
import com.example.demo.service.BookParserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
//import org.springframework.web.reactive.function.client.WebClient;


@RestController
public class RequestController {
    BookParserService scraperService;

    @GetMapping("/books/{id}")
    public String parsing(HttpServletRequest request) throws IncorrectBookYear, IncorrectBookID, IOException, IncorrectBookISBN {
       // database operations
         return scraperService.parseBookPage(request.getRequestURL().toString());
    }
}
