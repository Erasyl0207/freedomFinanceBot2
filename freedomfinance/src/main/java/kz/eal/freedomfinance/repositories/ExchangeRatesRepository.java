package kz.eal.freedomfinance.repositories;

import kz.eal.freedomfinance.entities.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates,Long> {

}
