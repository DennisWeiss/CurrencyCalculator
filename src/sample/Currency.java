package sample;

/**
 * Created by weiss on 30.11.2016.
 */

public class Currency {
    String name;
    String code;
    double rateToEUR;
    boolean isCustomCurrency;

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
        isCustomCurrency = false;
    }

    public void setCurrency(String name, String code) {
        this.name = name;
        this.code = code;
        isCustomCurrency = false;
    }

    public void setCurrency(String name, double rateToEUR) {
        this.name = name;
        this.isCustomCurrency = true;
        this.rateToEUR = rateToEUR;
    }

    public void setRateToEUR(double rateToEUR) {
        this.rateToEUR = rateToEUR;
    }
}

