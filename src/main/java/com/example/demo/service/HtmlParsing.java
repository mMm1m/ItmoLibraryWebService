package com.example.demo.service;

import org.jsoup.nodes.Document;
// common html parser for all libraries_html
public interface HtmlParsing {
    boolean checkGenre(Document document);
    boolean checkIsCorrectShop(boolean criteria1, boolean criteria2);
}
