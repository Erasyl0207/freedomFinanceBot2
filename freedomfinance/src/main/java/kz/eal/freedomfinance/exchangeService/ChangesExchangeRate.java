package kz.eal.freedomfinance.exchangeService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ChangesExchangeRate {


    public String getChanges(String currency,String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate tempDate = LocalDate.parse(date, formatter);
        String day = tempDate.minusDays(1).toString();
        String week = tempDate.minusWeeks(1).toString();
        String month = tempDate.minusMonths(1).toString();
        String year = tempDate.minusYears(1).toString();
        String fiveYear = tempDate.minusYears(5).toString();


        return "KZT to " + currency + " in " + date + "\n"
                + "Last day: " + parseHTML(currency,date,day) + "\n"
                + "Last week: " + parseHTML(currency,date,week)  + "\n"
                + "Last month: " + parseHTML(currency,date,month) + "\n"
                + "Last year: " + parseHTML(currency,date,year) + "\n"
                + "Over 5 years: " + parseHTML(currency,date,fiveYear) + "\n";
    }

    private String parseHTML(String currency,String firstDate,String secondDate){
        double valueKZT = 0.0,valueKZT2 = 0.0;
        try {
            valueKZT = getValueKZT(currency, firstDate);
            valueKZT2 = getValueKZT(currency, secondDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calculatePercentage(valueKZT,valueKZT2) + "\n"
                + "[" + firstDate + "] ---> 1 KZT = "
                + valueKZT + " " + currency + "\n["
                + secondDate + "] ---> 1 KZT = "
                + valueKZT2 + " " + currency + "\n";
    }

    public double getValueKZT(String currency, String date) throws IOException {

        String URL = "https://www.x-rates.com/historical/?from=KZT&amount=1&date=";
        Document doc = Jsoup.connect(URL + date).get();
        Element table = doc.getElementsByTag("tbody").get(1);
        double value = 0.0;

        for (int i = 0; i < table.getElementsByTag("tr").size() - 1; i++) {
            String cur = table.getElementsByTag("tr").get(i).getElementsByTag("td").get(0).wholeText();
            if (cur.equals(currency)) {
                value = Double.parseDouble(table.getElementsByTag("tr").get(i).getElementsByTag("td").get(1).wholeText());
                break;
            }
        }
        return value;
    }

    private String calculatePercentage(double x,double y){
        double ans = ((x-y)/y)*100;
        String res = String.format("%.3f",ans);
        return "\nPercentage changes: " + res + "%";
    }
}
