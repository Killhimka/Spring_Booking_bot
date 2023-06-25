package com.example.spring_booking_bot;

import com.example.spring_booking_bot.commands.*;
import com.example.spring_booking_bot.commands.bookcommand.*;
import com.example.spring_booking_bot.commands.LatLon;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    /*final
    UserRepo userRepo;

    public Bot(UserRepo userRepo) {
        this.userRepo = userRepo;
    }*/

    /*public Bot(String myMedicineSpringBot, String s) {
    }*/

    @Override
    public String getBotUsername() {
        return "my_medicine_spring_bot";
    }

    public Bot() {
    }

    @Override
    public String getBotToken(){
        return "6208575806:AAEgQuDfomdLJOyRZqQN3bDbNnrU5QsE0vU";
    }
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if (update.hasMessage() && update.getMessage().hasText()) {

        }

        if (!update.getMessage().getText().equals("Записаться")){
            Openweathermap oW = new Openweathermap(messageText);
            oW.Weather();
            sendMessage(chatId,oW.getAnswer());
        }
            if (messageText.equals("Записаться")) {
                KeyboardRow k = new KeyboardRow();
                k.add(new KeyboardButton("Log in"));
                k.add(new KeyboardButton("Записаться к врачу"));

                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(update.getMessage().getChatId().toString());
                sendMessage1.setText("Выберите действие");


                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
                sendMessage1.setReplyMarkup(replyKeyboardMarkup);

//Регистрация команд в листе
                List<WorKerCommand> list = new ArrayList<>();
                list.add(new LoginCommand());
                list.add(new BookCommand());
                list.add(new TerapevtBookCommand());
                list.add(new OkulistBookCommand());
                list.add(new LorBookCommand());
                list.add(new HirurgBookCommand());
                list.add(new GinekologBookCommand());
                list.add(new AlergolocBookCommand());
                list.add(new ChooseTime());
                for (WorKerCommand w : list) {
                    if (w.start(update) != null) {
                        sendMessage1 = w.start(update);
                        break;
                    }
                }

                try {
                    execute(sendMessage1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    private void sendMessage (long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        }
        catch (TelegramApiException e){

        }
    }
}
