package kz.eal.freedomfinance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double kztToUsd;
    private Double kztToEur;
    private Double kztToRub;
    private Double usdToKzt;
    private Double eurToKzt;
    private Double rubToKzt;
    private Double changesKztToUsdInTenDays;
    private Double changesKztToEurInTenDays;
    private Double changesKztToRubInTenDays;
    private Timestamp time;
}
