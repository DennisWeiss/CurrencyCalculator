package sample;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

import org.json.*;
import sun.rmi.runtime.Log;


public class DataFetcher {
    /**
     * method to write the JSON Object from the exchange rate API into a file
     */
    public static void writeInFile() {
        try{
            URL url = new URL("http://api.fixer.io/latest");

            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();

            PrintWriter writer = new PrintWriter("exchangeRates.json", "UTF-8");
            writer.println(str);
            writer.close();
        } catch (Exception e) {
        }
    }

    /**
     * method to set the rateToEUR of a Currency
     * @param currency which is supposed to get the exchange rate for
     */
    public static void getExchangeRate(Currency currency) {
        if (currency.getCode().equals("EUR")) {
            currency.setRateToEUR(1.0);
        } else {
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

            currency.setRateToEUR(rates.getDouble(currency.getCode()));
        }
    }
}
