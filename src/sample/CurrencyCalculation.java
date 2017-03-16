package sample;


/**
 * Created by weiss on 3/9/2017.
 */
public class CurrencyCalculation implements Calculation {
    public String exchange(Currency firstCurrency, Currency secondCurrency, double firstMoneySet) {
        double money;

        try {
            money = Math.round(firstMoneySet * secondCurrency.rateToEUR / firstCurrency.rateToEUR * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            money = 0.0;
        }

        return ensureTwoDecimals(money);
    }

    public String ensureTwoDecimals(double money) {
        double tenTimesMoney = 10 * money;
        if (tenTimesMoney == (int)tenTimesMoney) {
            return Double.toString(money)+"0";
        } else {
            return Double.toString(money);
        }
    }
}
