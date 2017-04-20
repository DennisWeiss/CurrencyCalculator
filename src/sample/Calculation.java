package sample;

/**
 * Created by weiss on 3/9/2017.
 */
public interface Calculation {
    /**
     *
     * @param firstCurrency the first currency as currency object
     * @param secondCurrency the second currency as currency object
     * @param firstMoneySet the amount of money of the first currency
     * @return the amount of money of the second currency
     */
    String exchange(Currency firstCurrency, Currency secondCurrency, double firstMoneySet);

    /**
     * method to make sure that the result is always given with two decimals
     * @param money as double value
     * @return amount of money as String with two decimals
     */
    String ensureTwoDecimals(double money);
}
