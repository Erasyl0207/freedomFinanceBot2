package kz.eal.freedomfinance.btn;

import kz.eal.freedomfinance.currencyStorage.CurrencyValues;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Buttons {

    public ReplyKeyboardMarkup mainButtons(boolean status){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow buttons = new KeyboardRow();

        buttons.add(new KeyboardButton("USD"));
        buttons.add(new KeyboardButton("EUR"));
        buttons.add(new KeyboardButton("RUB"));
        keyboardRowList.add(buttons);

        KeyboardRow subscribe = new KeyboardRow();
        if (status){
            subscribe.add(new KeyboardButton("Unsubscribe"));
        }else{
            subscribe.add(new KeyboardButton("Subscribe"));
        }
        keyboardRowList.add(subscribe);



        KeyboardRow moreButton = new KeyboardRow();
        moreButton.add(new KeyboardButton("More"));
        keyboardRowList.add(moreButton);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;

    }

    public ReplyKeyboardMarkup moreButtons(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow mainButton = new KeyboardRow();
        mainButton.add(new KeyboardButton("Main menu"));
        keyboardRowList.add(mainButton);

        CurrencyValues values = new CurrencyValues();
        int cnt = 0;
        KeyboardRow buttons = new KeyboardRow();
        for(Map.Entry<String,String> map : values.getCurrencies().entrySet()){
            cnt++;
            buttons.add(new KeyboardButton(map.getKey()));
            if (cnt == 4){
                cnt = 0;
                keyboardRowList.add(buttons);
                buttons = new KeyboardRow();
            }
        }
        if (cnt > 0){
            keyboardRowList.add(buttons);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }
}
