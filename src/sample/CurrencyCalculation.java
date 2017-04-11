package sample;


/**
 * class for implementation for Calculation
 */
public class CurrencyCalculation implements Calculation {
    /**
     *
     * @param firstCurrency
     * @param secondCurrency
     * @param firstMoneySet the amount of money
     * @return secondMoneySet as a String
     */
    public String exchange(Currency firstCurrency, Currency secondCurrency, double firstMoneySet) {
        double money;

        try {
            money = Math.round(firstMoneySet * secondCurrency.getRateToEUR() / firstCurrency.getRateToEUR() * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            money = 0.0;
        }

        return ensureTwoDecimals(money);
    }

    /**
     * makes sure that there are always 2 digits after the decimal point.
     * @param money
     * @return
     */
    public String ensureTwoDecimals(double money) {
        double tenTimesMoney = 10 * money;
        if (tenTimesMoney == (int)tenTimesMoney) {
            return Double.toString(money)+"0";
        } else {
            return Double.toString(money);
        }
    }
}
