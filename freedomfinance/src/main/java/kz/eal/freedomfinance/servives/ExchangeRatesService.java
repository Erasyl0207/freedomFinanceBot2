package kz.eal.freedomfinance.servives;

import kz.eal.freedomfinance.entities.ExchangeRates;

import java.util.List;

public interface ExchangeRatesService {
    List<ExchangeRates> getAllExchangeRates();
    void addExchangeRate(ExchangeRates exchangeRates);

}
