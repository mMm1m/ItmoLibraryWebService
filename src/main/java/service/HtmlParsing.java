package service;

import org.jsoup.nodes.Document;

import javax.print.Doc;

public interface HtmlParsing {
    boolean checkShopName(Document document);
    boolean checkGenre(Document document);
   // boolean checkIsLabyrinth(Document document);


}
