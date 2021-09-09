package kz.eal.freedomfinance.servives.impl;

import kz.eal.freedomfinance.entities.ExchangeRates;
import kz.eal.freedomfinance.repositories.ExchangeRatesRepository;
import kz.eal.freedomfinance.servives.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    @Autowired
    ExchangeRatesRepository exchangeRatesRepository;

    @Override
    public List<ExchangeRates> getAllExchangeRates() {
        return exchangeRatesRepository.findAll();
    }

    @Override
    public void addExchangeRate(ExchangeRates exchangeRates) {
        exchangeRatesRepository.save(exchangeRates);
    }
}
