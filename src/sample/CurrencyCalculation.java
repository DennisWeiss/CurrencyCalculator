package sample;


/**
 * class for implementation for Calculation
 */
public class CurrencyCalculation implements Calculation {
    @Override
    public String exchange(Currency firstCurrency, Currency secondCurrency, double firstMoneySet) {
        double money;

        try {
            money = Math.round(firstMoneySet * secondCurrency.getRateToEUR() / firstCurrency.getRateToEUR() * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            money = 0.0;
        }

        return ensureTwoDecimals(money);
    }

    @Override
    public String ensureTwoDecimals(double money) {
        double tenTimesMoney = 10 * money;
        if (tenTimesMoney == (int)tenTimesMoney) {
            return Double.toString(money)+"0";
        } else {
            return Double.toString(money);
        }
    }
}
