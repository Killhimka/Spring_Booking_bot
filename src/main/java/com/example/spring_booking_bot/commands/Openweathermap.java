package com.example.spring_booking_bot.commands;

import lombok.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Openweathermap implements WorKerCommand{
    private String city;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public Openweathermap(String city) {
        this.city = city;
    }

    public @NonNull String Weather() throws JSONException, IOException {
        LatLon latLon = new LatLon(city);
        latLon.latLonString(city);
        latLon.latLonList();

        /*System.out.println(latLon.latLonString(city));
        System.out.println(latLon.latLonList());
        System.out.println(latLon.getLat());
        System.out.println(latLon.getLon());*/
        URL url = new URL(
                "https://api.openweathermap.org/data/2.5/weather?lat="+latLon.getLat()+
                        "&lon="+latLon.getLon()+"&appid=71c96f4bb2d19d04e134aa2b72afde9c"
        );
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(url.openStream()));
        String str = bufferedReader.readLine();
        JSONObject jsonObject = new JSONObject(str);

        JSONObject jsonObject1 = (JSONObject) jsonObject.get("main");

        String temp = String.valueOf(jsonObject1.get("temp"));
        String feelTemp = String.valueOf(jsonObject1.get("feels_like"));
        String minTemp = String.valueOf(jsonObject1.get("temp_min"));
        String maxTemp = String.valueOf(jsonObject1.get("temp_max"));
        answer = city+
                ", сейчас " +(Math.round(Double.valueOf(temp)-273))+"\n"+
                "По ощущениям "+(Math.round(Double.valueOf(feelTemp)-273))+"\n"+
                "Минимальная температура "+(Math.round(Double.valueOf(minTemp)-273))+"\n"+
                "Максимальная температура "+(Math.round(Double.valueOf(maxTemp)-273))+"\n";

        return answer;
    }


    @Override
    public SendMessage start(Update update) {
        return null;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
