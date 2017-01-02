package sample;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

import org.json.*;
import sun.rmi.runtime.Log;

public class DataFetcher {
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

    public static void getExchangeRate(Currency currency) {
        if (currency.code.equals("EUR")) {
            currency.rateToEUR = 1.0;
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

            currency.rateToEUR = rates.getDouble(currency.code);
        }
    }
}
