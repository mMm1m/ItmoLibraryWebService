package service;

import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class HtmlParsingImpl implements LabyrinthHtmlParsing {
    @Autowired
    private HtmlParsing parsing = new HtmlParsingImpl();
    @Autowired
    private Document document;

    @Override
    public boolean checkIsLabyrinth(boolean firstCriteria, boolean secondCriteria) {
        return firstCriteria && secondCriteria;
    }

    @Override
    public boolean checkShopName(org.jsoup.nodes.Document document) {
        Elements elementClass = document.getElementsByClass("b-header-b-logo-e-logo-wrap");
        String shopName = "Лабиринт - самый большой книжный интернет магазин";

        for(var a : elementClass)
            if(a.attr("title").equals(shopName)) return true;
        return false;
    }

    @Override
    public boolean checkGenre(org.jsoup.nodes.Document document) {
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

    public static void increase(String[] arr, int curr)
    {
        String[] arr_ = new String[arr.length*2];
        if(arr.length == curr+1)  System.arraycopy(arr, 0, arr_, 0, arr.length);
        arr = arr_;
    }

    public static void fill(StringBuilder tmp, String bookName, String substring,  String[] ans, int idx)
    {
        tmp.append(substring).append(bookName);
        ans[idx++] = tmp.toString();
        tmp = new StringBuilder();
    }

    public String[] getParams() throws ParseException {
        String[] ans = new String[1];
        int idx = 0;
        StringBuilder tmp = new StringBuilder();
        // get book_name
        String bookName = document.getElementById("product-title").getElementsByTag("h1").text();
        increase(ans, idx);
        fill(tmp, bookName, "bookName : ", ans, idx);

        // get authors
        var authorTmp = document.getElementsByClass("authors");
        List<String> authors = new LinkedList<>();
        for (var a : authorTmp) {
            authors.add(a.getElementsByAttribute("data-event-content").text());
        }
        for(var a : authors)
        {
            increase(ans, idx);
            fill(tmp, a, "author : ", ans, idx);
        }

        String bookISBN = document.getElementsByClass("isbn").text();
        String bookID = document.getElementsByClass("articul").text();

        // ISBN
        increase(ans, idx);
        fill(tmp, bookISBN, "bookISBN : ", ans, idx);

        for (int i = bookID.length() - 1; i >= 0; --i) {
            Character curr = bookID.charAt(i);
            if (Character.isDigit(curr)) {
                increase(ans, idx);
                fill(tmp, String.valueOf(curr), "BookID : ", ans, idx);
            }
            else break;
        }
        if (!tmp.isEmpty()) {
            increase(ans, idx);
            fill(tmp, tmp.reverse().toString(), "bookID : ", ans, idx);
        } else throw new NullPointerException();


        String year = document.getElementsByClass("publisher").text();
        for (int i = year.length() - 1; i >= 0; --i) {
            Character curr = year.charAt(i);
            if (Character.isDigit(curr)) {
                increase(ans, idx);
                fill(tmp, String.valueOf(curr), "Publisher : ", ans, idx);
            }
        }
        if (!tmp.isEmpty()) {
            increase(ans, idx);
            fill(tmp, String.valueOf(tmp.reverse().toString()), "YearInteger : ", ans, idx);
        } else throw new NumberFormatException();
        return ans;
    }

    // while string, but later it will be HTML or JSON
    public String[] parseHtml()
    {
        try
        {
            if(checkIsLabyrinth(parsing.checkGenre(document) , parsing.checkShopName(document)))
            {
                return getParams();
            }
        }
        catch (NullPointerException npe)
        {
            System.out.println("Your error: " + "is NullPointerException ") ;
            npe.printStackTrace();
        }
        catch (Exception exception)
        {
            System.out.println("Your error: " + "is BaseException ");
            exception.printStackTrace();
        }
        return null;
    }
}
