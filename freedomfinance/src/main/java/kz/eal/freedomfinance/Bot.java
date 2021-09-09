package kz.eal.freedomfinance;

import kz.eal.freedomfinance.btn.Buttons;
import kz.eal.freedomfinance.currencyStorage.CurrencyValues;
import kz.eal.freedomfinance.entities.Users;
import kz.eal.freedomfinance.exchangeService.ChangesExchangeRate;
import kz.eal.freedomfinance.servives.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    UsersService usersService;

    @Autowired
    CurrencyValues values;

    @Autowired
    ChangesExchangeRate changes;

    @Autowired
    Buttons button;

    Map<Long,String> chats = new HashMap<>();

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;


    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = "";

            long chatId = update.getMessage().getChatId();
            String receivedMessage = update.getMessage().getText();

            SendMessage message = new SendMessage();
            message.enableMarkdown(true);
            message.setChatId(String.valueOf(chatId));


            String currency = values.getCurrencies().get(receivedMessage);

            if (receivedMessage.equals("/start") || receivedMessage.equals("Main menu")) {

                Users user = usersService.getByChatId(chatId);
                if (user == null){
                    user = new Users(0L,chatId,false);
                    usersService.addUser(user);
                }

                text = "Select currencies or sign up for updates!";
                message.setReplyMarkup(button.mainButtons(user.getNotify()));

            } else if (receivedMessage.equals("/subscribe") || receivedMessage.equals("Subscribe")){

                Users user = usersService.getByChatId(chatId);

                if (user != null){
                    if (user.getNotify()){
                        text = "You are already subscribed to updates!";
                    }else{
                        user.setNotify(true);
                        usersService.updateUser(user);
                        text = "You have successfully subscribed to updates!\n" +
                                "*You will receive fresh updates every half hour!*";
                        message.setReplyMarkup(button.mainButtons(user.getNotify()));
                    }
                }else{
                    user = new Users(0L,chatId,true);
                    usersService.addUser(user);
                    text = "You have successfully subscribed to updates!\n" +
                            "*You will receive fresh updates every half hour!*";
                    message.setReplyMarkup(button.mainButtons(user.getNotify()));
                }

            } else if (receivedMessage.equals("/unsubscribe") || receivedMessage.equals("Unsubscribe")) {

                Users user = usersService.getByChatId(chatId);

                if (user != null){
                    if (user.getNotify()){
                        user.setNotify(false);
                        usersService.updateUser(user);
                        text = "You have successfully unsubscribed for updates!";
                    }else{
                        text = "You are already unsubscribed from updates!!";
                    }
                    message.setReplyMarkup(button.mainButtons(user.getNotify()));
                }else{
                    text = "/start";
                }


            } else if (receivedMessage.equals("More")){
                text = "Choose currency!";
                message.setReplyMarkup(button.moreButtons());
            }else if (currency != null) {

                text = "Enter the date in the format: yyyy-mm-dd\n"
                        + "_Example_: *2018-12-12*\n"
                        + "_Minimum date_: *2016-01-01*";

            }else if (isValidDate(receivedMessage)){

                String cur = values.getCurrencies().get(chats.get(chatId));

                if (cur != null) {
                    text = changes.getChanges(cur,receivedMessage);
                }else{
                    text = "First you need to choose a currency!";
                }
            } else{
                text = "*ERROR*\n"
                        + "_No such currency exists or the date format is incorrect!_";
            }
            message.setText(text);
            chats.put(chatId,receivedMessage);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyUsers(Long chatId,String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        execute(message);
    }


    private boolean isValidDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDateTime.now();
        String now = dateTime.format(formatter);
        try{
            LocalDate time = LocalDate.parse(date,formatter);
            LocalDate min = LocalDate.parse("2016-01-01",formatter);
            LocalDate max = LocalDate.parse(now,formatter);
            if (time.compareTo(min) < 0){
                return false;
            }
            if (time.compareTo(max) >= 0){
                return false;
            }
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
