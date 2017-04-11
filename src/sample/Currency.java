package sample;

/**
 * every currency is determined by a name, an official 3-digit code, a rate to Euro
 */
public class Currency {
    private String name;
    private String code;
    private double rateToEUR;
    private boolean isCustomCurrency;

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
        isCustomCurrency = false;
    }

    public Currency(String name, String code, double rateToEUR) {
        this.name = name;
        this.code = code;
        this.rateToEUR = rateToEUR;
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

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getRateToEUR() {
        return rateToEUR;
    }

    public boolean isCustomCurrency() {
        return isCustomCurrency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCustomCurrency(boolean customCurrency) {
        isCustomCurrency = customCurrency;
    }

    public void setRateToEUR(double rateToEUR) {
        this.rateToEUR = rateToEUR;
    }
}

