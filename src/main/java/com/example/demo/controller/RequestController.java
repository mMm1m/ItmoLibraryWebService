package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;

@RestController
@Getter
@Setter
public class RequestController {
    private Document document = null;
    private String url = null;
    public RequestController(){}
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public void getURL(HttpServletRequest request) throws IOException {
        this.url = request.getRequestURL().toString();
        this.document = Jsoup.connect(url).get();
        System.out.println();
    }
}
