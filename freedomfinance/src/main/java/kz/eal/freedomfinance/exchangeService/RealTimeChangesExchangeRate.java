package kz.eal.freedomfinance.exchangeService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RealTimeChangesExchangeRate {

    public double getExchangeRate(String currency){
        String value = "";
        try {
            Document doc = Jsoup.connect("https://markets.businessinsider.com/currencies/realtime-list/" + currency + "-kzt").get();
            value = doc.getElementsByClass("box table_distance realtime-pricebox").get(0).getElementsByClass("push-data price").get(0).wholeText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.parseDouble(value);
    }


    public double convertToKzt(double exchange){
        double value = 1.0/exchange;
        value = Double.parseDouble(String.format("%.6f",value).replace(",","."));
        return value;
    }


    public double calculatePercentage(double x,double y){
        double value = ((x-y)/y)*100;
        value = Double.parseDouble(String.format("%.3f",value).replace(",","."));
        return value;
    }
}
