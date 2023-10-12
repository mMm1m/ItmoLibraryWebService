package com.example.demo;// подключение классов для работы с сетью

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.swing.*;
import java.net.*;
//классы для работы с сетью
import java.io.*;
import java.util.*;

public class LibraryServer {
    // book-names
    public Pair<List<Object>, List<String>> parseHTML(String url ) throws IOException {
        // Create document-object, which source for other select-methods
        Document doc = Jsoup.connect(url).get();
        try
        {
            if(checkIsLabyrinth(doc))
            {
                var list = getParams(doc);
                return list;
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(null, null);
    }

    // check labyrinth-shop-name
    public static boolean checkShopName(Document document)
    {
        Elements elementClass = document.getElementsByClass("b-header-b-logo-e-logo-wrap");
        String shopName = "Лабиринт - самый большой книжный интернет магазин";

        for(var a : elementClass)
            if(a.attr("title").equals(shopName)) return true;
        return false;
    }

    public static boolean checkGenre(Document document)
    {
        // check, is it IT-genre
        Element genre = document.getElementById("thermometer-books");
        System.out.println(genre);
        Elements select = genre.select("span");
        Set<String> idealSet = new HashSet<>();
        // main genre
        idealSet.add("Информационные технологии");
        // all others
        idealSet.add("Базы данных"); idealSet.add("Графика. Дизайн. Проектирование");
        idealSet.add("Железо ПК"); idealSet.add("Интернет");
        idealSet.add("Информатика"); idealSet.add("Машинное обучение. Анализ данных");
        idealSet.add("Менеджмент в IT"); idealSet.add("Операционные системы и утилиты для ПК");
        idealSet.add("Программирование"); idealSet.add("Программы и утилиты для цифровых устройств");
        idealSet.add("Руководства по пользованию программами"); idealSet.add("Сети и коммуникации");
        idealSet.add("Электронная бухгалтерия");
        int initSize = idealSet.size();
        for(var a : select)
        {
            var tmp = a.getElementsByAttribute("itemprop").text();
            if(idealSet.contains(tmp))
                idealSet.remove(tmp);
        }
        return (idealSet.size() == initSize) ? false : true ;
    }

    // return list of Pair.of( book_mane and author_name )
    // all params && all authors
    public static Pair<List<Object>, List<String>> getParams(Document document) throws Exception {
        List<Object> params = new LinkedList<>();
        // get book_name
        String bookName = document.getElementById("product-title").getElementsByTag("h1").text();
        params.add(bookName);

        // get authors
        var authorTmp = document.getElementsByClass("authors");
        List<String> authors = new LinkedList<>();
        for(var a : authorTmp)
        {
            authors.add(a.getElementsByAttribute("data-event-content").text());
        }
        List<String> authors_ = authors;

        String bookISBN = document.getElementsByClass("isbn").text();
        String bookID = document.getElementsByClass("articul").text();
        params.add(bookISBN);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = bookID.length()-1; i >= 0; --i)
        {
            Character curr = bookID.charAt(i);
            if(Character.isDigit(curr)) stringBuilder.append(Integer.parseInt(String.valueOf(curr)));
            else break;
        }
        if(!stringBuilder.isEmpty())
        {
            Integer bookIDInteger = Integer.parseInt(stringBuilder.reverse().toString());
            params.add(bookIDInteger);
        }
        else throw new NullPointerException();


        String year = document.getElementsByClass("publisher").text();
        stringBuilder = new StringBuilder();
        for(int i = year.length()-1; i >= 0; --i)
        {
            Character curr = year.charAt(i);
            if(Character.isDigit(curr)) stringBuilder.append(Integer.parseInt(String.valueOf(curr)));
        }
        if(!stringBuilder.isEmpty())
        {
            Integer yearInteger = Integer.parseInt(stringBuilder.reverse().toString());
            params.add(yearInteger);
        }
        else throw new NumberFormatException();

        return new Pair<>(params, authors_);
    }


    public static boolean checkIsLabyrinth(Document document)
    {
        return checkShopName(document) && checkGenre(document);
    }
}

