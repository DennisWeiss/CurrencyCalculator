package sample;

/**
 * Created by weiss on 3/9/2017.
 */
public interface Calculation {
    String exchange(Currency firstCurrency, Currency secondCurrency, double firstMoneySet);
    String ensureTwoDecimals(double money);
}
