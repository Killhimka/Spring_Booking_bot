package com.example.spring_booking_bot.commands;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatLon {
    String city;
    String lat;
    String lon;

    public String getLat() {
        return lat = String.valueOf(latLonList().get(0));
    }

    public String getLon() {
        return lon = String.valueOf(latLonList().get(1));
    }

    public LatLon(String city) {
        this.city = city;
    }

    public String latLonString (String str) {
        Elements elements1 = null;
        try {
            String name = null;
            Document doc = Jsoup.connect("https://ru.wikipedia.org/wiki/"+city).get();
            Elements table = doc.select("div.mw-parser-output");
            Elements elements = table.select("span.coordinates");
            elements1 = elements.select("a.mw-kartographer-maplink");
        } catch (Exception e){
            System.err.println("Города с названием "+city+" не существует");
        }
        /*System.out.println(elements1);*/

        String text = String.valueOf(elements1);

        Pattern p = Pattern.compile("(data\\-lat\\=\\\"\\d{1,3}\\.\\d{1,12}\\\")|" +
                "(data\\-lat\\=\\\"\\-\\d{1,3}\\.\\d{1,12}\\\")|"+
                "(data\\-lat\\=\\\"\\-\\d{1,3}\\\"|)"+
                "(data\\-lat\\=\\\"\\d{1,3}\\\")");
        Pattern p1 = Pattern.compile("(data\\-lon\\=\\\"\\d{1,3}\\.\\d{1,12}\\\")|" +
                "(data\\-lon\\=\\\"\\-\\d{1,3}\\.\\d{1,12}\\\")|"+
                "(data\\-lon\\=\\\"\\d{1,3}\\\")|"+
                "(data\\-lon\\=\\\"\\-\\d{1,3}\\\")");
        Matcher m = p.matcher(text);
        Matcher m1 = p1.matcher(text);

        String latLon = null;
        try {
        if (m.find()&&m1.find()) {
            latLon = m.group() + " " + m1.group();
        }   else {
            System.out.println("Ничего не найдено");
        }
        }catch (Exception e ){
            throw new RuntimeException();
        }

        return latLon;
    }
    public List latLonList (){
        List<String> latLonList;
        try {
            latLonList = new ArrayList<>();
            Pattern p = Pattern.compile("\\d{1,3}\\.\\d{1,12}|\\-\\d{1,3}\\.\\d{1,12}|" +
                    "\\d{1,3}|\\-\\d{1,3}");
            /*System.out.println(city);*/
            Matcher m = p.matcher(latLonString(city));
            while (m.find()) {
                latLonList.add(m.group());
            }
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
        return latLonList;
    }
}
