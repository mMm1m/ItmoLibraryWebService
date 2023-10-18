package controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Controller
public class RequestController {

    // get html from url with 2 methods
    private String inputHtml = "https://www.labirint.ru/books/512969/";
    /*@RequestMapping(value = "/books",method = RequestMethod.GET)
    @ResponseBody
    public String getHtml(HttpServletRequest request) throws IOException {
        String urlInTextFormat = request.getRequestURL().toString();
        System.out.println(Jsoup.connect(urlInTextFormat).get().html());
        return Jsoup.connect(urlInTextFormat).get().html();
    }*/

    @RequestMapping(value = "/books",method = RequestMethod.GET)
    @ResponseBody
    public String getURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /*@GetMapping
    public String getHTMLFromHTTPClient() throws IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.labirint.ru/books/512969/"))
                    .GET() // GET is default
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response.body();
    }*/

}
