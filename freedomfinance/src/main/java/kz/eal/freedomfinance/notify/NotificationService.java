package kz.eal.freedomfinance.notify;

import kz.eal.freedomfinance.Bot;
import kz.eal.freedomfinance.entities.ExchangeRates;
import kz.eal.freedomfinance.entities.Users;
import kz.eal.freedomfinance.exchangeService.ChangesExchangeRate;
import kz.eal.freedomfinance.exchangeService.RealTimeChangesExchangeRate;
import kz.eal.freedomfinance.servives.ExchangeRatesService;
import kz.eal.freedomfinance.servives.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NotificationService {

    @Autowired
    Bot bot;

    @Autowired
    RealTimeChangesExchangeRate realTimeChangesExchangeRate;

    @Autowired
    ChangesExchangeRate changesExchangeRate;

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @Autowired
    UsersService usersService;

    @Scheduled(fixedRate = 1800000)
    public void getChangesExchange() throws TelegramApiException, IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String tenDays = LocalDate.now().minusDays(10).format(format);
        String month = LocalDate.now().minusMonths(1).format(format);
        String year = LocalDate.now().minusYears(1).format(format);


        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        double eurToKzt = realTimeChangesExchangeRate.getExchangeRate("eur");
        double usdToKzt = realTimeChangesExchangeRate.getExchangeRate("usd");
        double rubToKzt = realTimeChangesExchangeRate.getExchangeRate("rub");

        double kztToEur = realTimeChangesExchangeRate.convertToKzt(eurToKzt);
        double kztToUsd = realTimeChangesExchangeRate.convertToKzt(usdToKzt);
        double kztToRub = realTimeChangesExchangeRate.convertToKzt(rubToKzt);


        double changesInTenDaysUsd = realTimeChangesExchangeRate.calculatePercentage(kztToUsd,changesExchangeRate.getValueKZT("US Dollar",tenDays));
        double changesInTenDaysEur = realTimeChangesExchangeRate.calculatePercentage(kztToEur,changesExchangeRate.getValueKZT("Euro",tenDays));
        double changesInTenDaysRub = realTimeChangesExchangeRate.calculatePercentage(kztToRub,changesExchangeRate.getValueKZT("Russian Ruble",tenDays));

        double changesInMonthUsd = realTimeChangesExchangeRate.calculatePercentage(kztToUsd,changesExchangeRate.getValueKZT("US Dollar",month));
        double changesInMonthEur = realTimeChangesExchangeRate.calculatePercentage(kztToEur,changesExchangeRate.getValueKZT("Euro",month));
        double changesInMonthRub = realTimeChangesExchangeRate.calculatePercentage(kztToRub,changesExchangeRate.getValueKZT("Russian Ruble",month));

        double changesInYearUsd = realTimeChangesExchangeRate.calculatePercentage(kztToUsd,changesExchangeRate.getValueKZT("US Dollar",year));
        double changesInYearEur = realTimeChangesExchangeRate.calculatePercentage(kztToEur,changesExchangeRate.getValueKZT("Euro",year));
        double changesInYearRub = realTimeChangesExchangeRate.calculatePercentage(kztToRub,changesExchangeRate.getValueKZT("Russian Ruble",year));

        List<Users> users = usersService.getAllSubscribedUsers();

        StringBuilder builder = new StringBuilder();
        builder.append("*Changes in the exchange rate to KZT right now!*\n");
        builder.append("\n1 KZT ---> ").append(kztToUsd).append(" USD\n");
        builder.append("1 KZT ---> ").append(kztToEur).append(" EUR\n");
        builder.append("1 KZT ---> ").append(kztToRub).append(" RUB\n");
        builder.append("\n1 USD ---> ").append(usdToKzt).append(" KZT\n");
        builder.append("1 EUR ---> ").append(eurToKzt).append(" KZT\n");
        builder.append("1 RUB ---> ").append(rubToKzt).append(" KZT\n");
        builder.append("\n*Percentage change in the last 10 days!*\n");
        builder.append("\nKZT/USD  --->  ").append(changesInTenDaysUsd).append("%\n");
        builder.append("KZT/EUR  --->  ").append(changesInTenDaysEur).append("%\n");
        builder.append("KZT/RUB  --->  ").append(changesInTenDaysRub).append("%\n");
        builder.append("\n*Percentage change in the last month!*\n");
        builder.append("\nKZT/USD  --->  ").append(changesInMonthUsd).append("%\n");
        builder.append("KZT/EUR  --->  ").append(changesInMonthEur).append("%\n");
        builder.append("KZT/RUB  --->  ").append(changesInMonthRub).append("%\n");
        builder.append("\n*Percentage change in the last year!*\n");
        builder.append("\nKZT/USD  --->  ").append(changesInYearUsd).append("%\n");
        builder.append("KZT/EUR  --->  ").append(changesInYearEur).append("%\n");
        builder.append("KZT/RUB  --->  ").append(changesInYearRub).append("%\n");

        for (Users u:users) {
            bot.notifyUsers(u.getChatId(),builder.toString());
        }

        ExchangeRates exchangeRates = new ExchangeRates(0L,kztToUsd,kztToEur,kztToRub,usdToKzt,eurToKzt,rubToKzt,changesInTenDaysUsd,changesInTenDaysEur,changesInTenDaysRub,dateNow);
        exchangeRatesService.addExchangeRate(exchangeRates);
    }
}
