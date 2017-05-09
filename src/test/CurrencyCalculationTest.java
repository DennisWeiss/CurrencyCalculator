package test;

import sample.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by weiss on 3/9/2017.
 */
class CurrencyCalculationTest {
    @org.junit.jupiter.api.Test
    void exchange() {
        Calculation currencyCalculation = CurrencyEnum.USDollar;
        Currency euro = new Currency("Euro", "EUR");
        euro.setRateToEUR(1);
        Currency currency1 = new Currency("Test", "XYZ");
        currency1.setRateToEUR(2.5);
        Currency currency2 = new Currency("Test2", "XYZ");
        currency2.setRateToEUR(0.82);
        assertEquals("25.00", currencyCalculation.exchange(euro, currency1, 10));
        assertEquals("82.00", currencyCalculation.exchange(euro, currency2, 100));
    }

    @org.junit.jupiter.api.Test
    void ensureTwoDecimals() {
        Calculation currencyCalculation = CurrencyEnum.USDollar;
        double number1 = 2.4;
        double number2 = -156;
        assertEquals("2.40", currencyCalculation.ensureTwoDecimals(number1));
        assertEquals("-156.00", currencyCalculation.ensureTwoDecimals(number2));
    }

}