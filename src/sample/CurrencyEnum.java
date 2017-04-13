package sample;

import org.json.JSONObject;

import java.io.*;

/**
 * CurrencyEnum for enum implementation
 */
public enum CurrencyEnum implements Calculation{
    Euro("EUR"), USDollar("USD"), BritishPound("GBP"), SwissFranc("CHF"), AustralianDollar("AUD"), BulgarianLev("BGN"),
    BrazilianReal("BRL"), CanadianDollar("CAD"), ChineseYuan("CNY"), CzechRepublicKoruna("CZK"), DanishKrone("DKK"),
    HongKongDollar("HKD"), CroatianKuna("HRK"), HungarianForint("HUF"), IndonesianRupiah("IDR"), IsraeliNewSheqel("ILS"),
    IndianRupee("INR"), JapaneseYen("JPY"), SouthKoreanWon("KRW"), MexicanPeso("MXN"), MalaysianRinggit("MYR"), NorwegianKrone("NOK"),
    NewZealandDollar("NZD"), PhilippinePeso("PHP"), PolishZloty("PLN"), RomanianLeu("RON"), RussianRuble("RUB"), SwedishKrona("SEK"),
    SingaporeDollar("SGD"), ThaiBaht("THB"), TurkishLira("TRY"), SouthAfricanRand("ZAR");

    private String code;
    private double rateToEUR;

    CurrencyEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public double getRateToEUR() {
        return rateToEUR;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRateToEUR(double rateToEUR) {
        this.rateToEUR = rateToEUR;
    }

    /**
     * method to update the rateToEUR from the API data.
     */
    public void updateRateToEUR() {
        DataFetcher.writeInFile();

        String json = null;
        try {
            InputStream stream = new FileInputStream("exchangeRates.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            json = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(json);
        JSONObject rates = obj.getJSONObject("rates");

        rateToEUR = rates.getDouble(code);
    }

    /**
     * takes two currencies and one set of money and returns the other set of money
     * @param firstCurrency the currency of the first money set
     * @param secondCurrency the  currency of the second money set
     * @param firstMoneySet
     * @return amount of money as string
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
     * makes sure that there are always two decimals behind the decimal point
     * @param money the amount of money as a double value
     * @return amount of money as a string
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
