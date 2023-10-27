package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.client.WebClient;
import service.LabyrinthITParsingImpl;
import service.LabyrinthParsingHtml;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.swing.text.html.HTML;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
public class RequestController {
     public Document document = null;
    public String url = null;
    public RequestController(){}
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public void getURL(HttpServletRequest request) throws IOException {
        this.url = request.getRequestURL().toString();
        this.document = Jsoup.connect(url).get();
    }
}
