package sample;

import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.DoubleBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class Main extends Application {

    final ComboBox currency1 = new ComboBox();
    final ComboBox currency2 = new ComboBox();
    final GridPane gridpane = new GridPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        DataFetcher.writeInFile();

        VBox vbox = new VBox();
        MenuBar menubar = new MenuBar();
        final TextField firstMoneySet = new TextField();
        final TextField secondMoneySet = new TextField();

        secondMoneySet.setEditable(false);

        gridpane.setMargin(currency1, new Insets(10));
        gridpane.setMargin(currency2, new Insets(10));

        setComboBox(currency1);
        setComboBox(currency2);

        final Currency firstCurrency = new Currency("Euro", "EUR");
        final Currency secondCurrency = new Currency("Euro", "EUR");

        currency1.setValue("Euro");
        currency2.setValue("US Dollar");

        gridpane.setPadding(new Insets(50));

        gridpane.add(firstMoneySet, 0, 0);
        gridpane.add(secondMoneySet, 0, 1);
        gridpane.add(currency1, 1, 0);
        gridpane.add(currency2, 1, 1);

        Menu File = new Menu("File");

        MenuItem NewCurrency = new MenuItem("Add new currency");
        MenuItem deleteCurrency = new MenuItem("Delete a custom currency");
        MenuItem deleteCurrencies = new MenuItem("Delete all custom currencies");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem Exit = new MenuItem("Exit");

        File.getItems().addAll(NewCurrency, deleteCurrency, deleteCurrencies, separatorMenuItem, Exit);
        menubar.getMenus().addAll(File);

        NewCurrency.setOnAction(e ->
                newCurrencyWindow()
        );

        deleteCurrency.setOnAction(e ->
                deleteCurrencyWindow()
        );

        deleteCurrencies.setOnAction(e ->
                deleteAllCurrencies()
        );

        Exit.setOnAction(e ->
                System.exit(0)
        );

        vbox.getChildren().addAll(menubar, gridpane);

        currency1.valueProperty().addListener(e ->
                calculation(currency1, currency2, firstCurrency, secondCurrency, firstMoneySet, secondMoneySet)
        );

        currency2.valueProperty().addListener(e ->
                calculation(currency1, currency2, firstCurrency, secondCurrency, firstMoneySet, secondMoneySet)
        );

        firstMoneySet.textProperty().addListener((observable, oldText, newText) ->
                calculation(currency1, currency2, firstCurrency, secondCurrency, firstMoneySet, secondMoneySet)
        );

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setComboBox(ComboBox currency) {

        currency.getItems().clear();

        currency.getItems().addAll(
                "Euro",
                "US Dollar",
                "British Pound",
                "Swiss Franc",
                "Australian Dollar",
                "Bulgarian Lev",
                "Brazilian Real",
                "Canadian Dollar",
                "Chinese Yuan",
                "Czech Republic Koruna",
                "Danish Krone",
                "Hong Kong Dollar",
                "Croatian Kuna",
                "Hungarian Forint",
                "Indonesian Rupiah",
                "Israeli New Sheqel",
                "Indian Rupee",
                "Japanese Yen",
                "South Korean Won",
                "Mexican Peso",
                "Malaysian Ringgit",
                "Norwegian Krone",
                "New Zealand Dollar",
                "Philippine Peso",
                "Polish Zloty",
                "Romanian Leu",
                "Russian Ruble",
                "Swedish Krona",
                "Singapore Dollar",
                "Thai Baht",
                "Turkish Lira",
                "South African Rand"
        );

        BufferedReader reader = null;
        String line;
        ArrayList<String> lines = new ArrayList<String>(0);
        try {
            reader = new BufferedReader(new FileReader("customCurrencies"));
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % 4 == 1 && !currency.getItems().contains(line)) {
                    currency.getItems().add(line);
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("IOException 1");
        }
    }

    public void setComboBox(ComboBox currency, boolean onlyCustomsCurrencies) {

        BufferedReader reader = null;
        String line;
        ArrayList<String> lines = new ArrayList<String>(0);
        try {
            reader = new BufferedReader(new FileReader("customCurrencies"));
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % 4 == 1 && !currency.getItems().contains(line)) {
                    currency.getItems().add(line);
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("IOException 1");
        }
    }

    public void deleteCurrency(String currency) throws FileNotFoundException {
        BufferedReader reader = null;
        String line;
        ArrayList<String> strings = new ArrayList<>(0);

        try {
            reader = new BufferedReader(new FileReader("customCurrencies"));
            while ((line = reader.readLine()) != null) {
                strings.add(line);
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = 1;
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter("customCurrencies"));
            for (int i = 0; i < strings.size(); i++) {
                System.out.println(strings.get(i));
                if (strings.get(i).equals(currency)) {
                    index = i;
                    break;
                }
            }
            for (int i = 0; i < strings.size(); i++) {
                if (i != index-1 && i != index && i != index+1 && i != index+2) {
                    writer.write(strings.get(i)+"\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCurrencyWindow() {
        Stage secondaryStage = new Stage();
        StackPane pane = new StackPane();
        VBox vbox = new VBox();

        Label label = new Label("Select currency you would\nlike to delete:");
        ComboBox currencies = new ComboBox();
        Button delete = new Button("Delete");
        Label alert = new Label();

        label.setMaxWidth(180);
        currencies.setPrefWidth(180);
        delete.setPrefWidth(180);

        vbox.setPadding(new Insets(30, 10, 30, 10));
        vbox.setSpacing(20);

        vbox.getChildren().addAll(label, currencies, delete, alert);
        pane.getChildren().addAll(vbox);

        setComboBox(currencies, true);
        currencies.getSelectionModel().select(0);

        delete.setOnAction(e -> {
            String currencyName = currencies.getValue().toString();
            try {
                deleteCurrency(currencyName);
                alert.setText(currencyName + "\ndeleted successfully.");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            currencies.getItems().remove(currencyName);
            currency1.getItems().remove(currencyName);
            currency2.getItems().remove(currencyName);
        });

        Scene scene = new Scene(pane, 180, 230);
        secondaryStage.setScene(scene);
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    public void newCurrencyWindow() {
        Stage secondaryStage = new Stage();
        StackPane pane = new StackPane();

        VBox vbox = new VBox();

        TextField currencyName = new TextField();
        TextField rate = new TextField();
        currencyName.setPromptText("Name of currency");
        rate.setPromptText("Rate to EUR");

        HBox hbox = new HBox();

        Region region1 = new Region();
        Button ok = new Button("OK");
        Region region2 = new Region();
        Button abort = new Button("Abort");
        Region region3 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setHgrow(region3, Priority.ALWAYS);

        ok.setTranslateX(0);
        ok.setTranslateY(0);
        ok.setPrefWidth(70);

        abort.setTranslateX(0);
        abort.setTranslateY(0);
        abort.setPrefWidth(70);

        vbox.setPadding(new Insets(50, 10, 50, 10));
        vbox.setSpacing(10);

        hbox.getChildren().addAll(region1, ok, region2, abort, region3);
        vbox.getChildren().addAll(currencyName, rate, hbox);
        pane.getChildren().addAll(vbox);

        ok.setOnAction(e -> {
            okButton(currencyName.getText().toString(), Double.valueOf(rate.getText().toString()));
            secondaryStage.close();
                }
        );

        abort.setOnAction(e ->
                secondaryStage.close()
        );

        Scene scene = new Scene(pane, 250, 200);
        secondaryStage.setScene(scene);
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    void okButton(String name, double rate) {
        addCurrency(name, rate);
        setComboBox(currency1, true);
        setComboBox(currency2, true);
    }

    void addCurrency(String name, double rate) {
        BufferedReader reader = null;
        String line;
        ArrayList<String> lines = new ArrayList<String>(0);
        try {
            reader = new BufferedReader(new FileReader("customCurrencies"));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("IOException 1");
        }

        BufferedWriter writer;
        if (lines.contains(name)) {
            System.out.println("This currency does already exist!");
        } else {
            try {
                writer = new BufferedWriter(new FileWriter("customCurrencies", true));
                writer.append("{" + "\n");
                writer.append(name + "\n");
                writer.append(String.valueOf(rate) + "\n");
                writer.append("}" + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOException 2");
            }

        }
    }

    public void deleteAllCurrencies() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("customCurrencies"));
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        setComboBox(currency1);
        setComboBox(currency2);
    }

    static void calculation(ComboBox currency1, ComboBox currency2, Currency firstCurrency, Currency secondCurrency,
                            TextField firstMoneySet, TextField secondMoneySet) {
        if (currency1.getValue().toString().equals("Euro")) {
            firstCurrency.setCurrency("Euro", "EUR");
        } else if (currency1.getValue().toString().equals("US Dollar")) {
            firstCurrency.setCurrency("US Dollar", "USD");
        } else if (currency1.getValue().toString().equals("British Pound")) {
            firstCurrency.setCurrency("British pound", "GBP");
        } else if (currency1.getValue().toString().equals("Swiss Franc")) {
            firstCurrency.setCurrency("Swiss Franc", "CHF");
        } else if (currency1.getValue().toString().equals("Australian Dollar")) {
            firstCurrency.setCurrency("Australian Dollar", "AUD");
        } else if (currency1.getValue().toString().equals("Bulgarian Lev")) {
            firstCurrency.setCurrency("Bulgarian Lev", "BGN");
        } else if (currency1.getValue().toString().equals("Brazilian Real")) {
            firstCurrency.setCurrency("Brazilian Real", "BRL");
        } else if (currency1.getValue().toString().equals("Canadian Dollar")) {
            firstCurrency.setCurrency("Canadian Dollar", "CAD");
        } else if (currency1.getValue().toString().equals("Chinese Yuan")) {
            firstCurrency.setCurrency("Chinese Yuan", "CNY");
        } else if (currency1.getValue().toString().equals("Czech Republic Koruna")) {
            firstCurrency.setCurrency("Czech Republic Koruna", "CZK");
        } else if (currency1.getValue().toString().equals("Danish Krone")) {
            firstCurrency.setCurrency("Danish Krone", "DKK");
        } else if (currency1.getValue().toString().equals("Hong Kong Dollar")) {
            firstCurrency.setCurrency("Hong Kong Dollar", "HKD");
        } else if (currency1.getValue().toString().equals("Croatian Kuna")) {
            firstCurrency.setCurrency("Croatian Kuna", "HRK");
        } else if (currency1.getValue().toString().equals("Hungarian Forint")) {
            firstCurrency.setCurrency("Hungarian Forint", "HUF");
        } else if (currency1.getValue().toString().equals("Indonesian Rupiah")) {
            firstCurrency.setCurrency("Indonesian Rupiah", "IDR");
        } else if (currency1.getValue().toString().equals("Israeli New Sheqel")) {
            firstCurrency.setCurrency("Israeli New Sheqel", "ILS");
        } else if (currency1.getValue().toString().equals("Indian Rupee")) {
            firstCurrency.setCurrency("Indian Rupee", "INR");
        } else if (currency1.getValue().toString().equals("Japanese Yen")) {
            firstCurrency.setCurrency("Japanese Yen", "JPY");
        } else if (currency1.getValue().toString().equals("South Korean Won")) {
            firstCurrency.setCurrency("South Korean Won", "KRW");
        } else if (currency1.getValue().toString().equals("Mexican Peso")) {
            firstCurrency.setCurrency("Mexican Peso", "MXN");
        } else if (currency1.getValue().toString().equals("Malaysian Ringgit")) {
            firstCurrency.setCurrency("Malaysian Ringgit", "MYR");
        } else if (currency1.getValue().toString().equals("Norwegian Krone")) {
            firstCurrency.setCurrency("Norwegian Krone", "NOK");
        } else if (currency1.getValue().toString().equals("New Zealand Dollar")) {
            firstCurrency.setCurrency("New Zealand Dollar", "NZD");
        } else if (currency1.getValue().toString().equals("Philippine Peso")) {
            firstCurrency.setCurrency("Philippine Peso", "PHP");
        } else if (currency1.getValue().toString().equals("Polish Zloty")) {
            firstCurrency.setCurrency("Polish Zloty", "PLN");
        } else if (currency1.getValue().toString().equals("Romanian Leu")) {
            firstCurrency.setCurrency("Romanian Leu", "RON");
        } else if (currency1.getValue().toString().equals("Russian Ruble")) {
            firstCurrency.setCurrency("Russian Ruble", "RUB");
        } else if (currency1.getValue().toString().equals("Swedish Krona")) {
            firstCurrency.setCurrency("Swedish Krona", "SEK");
        } else if (currency1.getValue().toString().equals("Singapore Dollar")) {
            firstCurrency.setCurrency("Singapore Dollar", "SGD");
        } else if (currency1.getValue().toString().equals("Thai Baht")) {
            firstCurrency.setCurrency("Thai Baht", "THB");
        } else if (currency1.getValue().toString().equals("Turkish Lira")) {
            firstCurrency.setCurrency("Turkish Lira", "TRY");
        } else if (currency1.getValue().toString().equals("South African Rand")) {
            firstCurrency.setCurrency("South African Rand", "ZAR");
        } else {
            BufferedReader reader = null;
            String line;
            ArrayList<String> lines = new ArrayList<String>(0);
            try {
                reader = new BufferedReader(new FileReader("customCurrencies"));
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                for (int i = 1; i < lines.size(); i += 4) {
                    if (currency1.getValue().toString().equals(lines.get(i))) {
                        firstCurrency.setCurrency(lines.get(i), Double.valueOf(lines.get(i + 1)));
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("IOException 1");
            }
        }

        if (currency2.getValue().toString().equals("Euro")) {
            secondCurrency.setCurrency("Euro", "EUR");
        } else if (currency2.getValue().toString().equals("US Dollar")) {
            secondCurrency.setCurrency("US Dollar", "USD");
        } else if (currency2.getValue().toString().equals("British Pound")) {
            secondCurrency.setCurrency("British pound", "GBP");
        } else if (currency2.getValue().toString().equals("Swiss Franc")) {
            secondCurrency.setCurrency("Swiss Franc", "CHF");
        } else if (currency2.getValue().toString().equals("Australian Dollar")) {
            secondCurrency.setCurrency("Australian Dollar", "AUD");
        } else if (currency2.getValue().toString().equals("Bulgarian Lev")) {
            secondCurrency.setCurrency("Bulgarian Lev", "BGN");
        } else if (currency2.getValue().toString().equals("Brazilian Real")) {
            secondCurrency.setCurrency("Brazilian Real", "BRL");
        } else if (currency2.getValue().toString().equals("Canadian Dollar")) {
            secondCurrency.setCurrency("Canadian Dollar", "CAD");
        } else if (currency2.getValue().toString().equals("Chinese Yuan")) {
            secondCurrency.setCurrency("Chinese Yuan", "CNY");
        } else if (currency2.getValue().toString().equals("Czech Republic Koruna")) {
            secondCurrency.setCurrency("Czech Republic Koruna", "CZK");
        } else if (currency2.getValue().toString().equals("Danish Krone")) {
            secondCurrency.setCurrency("Danish Krone", "DKK");
        } else if (currency2.getValue().toString().equals("Hong Kong Dollar")) {
            secondCurrency.setCurrency("Hong Kong Dollar", "HKD");
        } else if (currency2.getValue().toString().equals("Croatian Kuna")) {
            secondCurrency.setCurrency("Croatian Kuna", "HRK");
        } else if (currency2.getValue().toString().equals("Hungarian Forint")) {
            secondCurrency.setCurrency("Hungarian Forint", "HUF");
        } else if (currency2.getValue().toString().equals("Indonesian Rupiah")) {
            secondCurrency.setCurrency("Indonesian Rupiah", "IDR");
        } else if (currency2.getValue().toString().equals("Israeli New Sheqel")) {
            secondCurrency.setCurrency("Israeli New Sheqel", "ILS");
        } else if (currency2.getValue().toString().equals("Indian Rupee")) {
            secondCurrency.setCurrency("Indian Rupee", "INR");
        } else if (currency2.getValue().toString().equals("Japanese Yen")) {
            secondCurrency.setCurrency("Japanese Yen", "JPY");
        } else if (currency2.getValue().toString().equals("South Korean Won")) {
            secondCurrency.setCurrency("South Korean Won", "KRW");
        } else if (currency2.getValue().toString().equals("Mexican Peso")) {
            secondCurrency.setCurrency("Mexican Peso", "MXN");
        } else if (currency2.getValue().toString().equals("Malaysian Ringgit")) {
            secondCurrency.setCurrency("Malaysian Ringgit", "MYR");
        } else if (currency2.getValue().toString().equals("Norwegian Krone")) {
            secondCurrency.setCurrency("Norwegian Krone", "NOK");
        } else if (currency2.getValue().toString().equals("New Zealand Dollar")) {
            secondCurrency.setCurrency("New Zealand Dollar", "NZD");
        } else if (currency2.getValue().toString().equals("Philippine Peso")) {
            secondCurrency.setCurrency("Philippine Peso", "PHP");
        } else if (currency2.getValue().toString().equals("Polish Zloty")) {
            secondCurrency.setCurrency("Polish Zloty", "PLN");
        } else if (currency2.getValue().toString().equals("Romanian Leu")) {
            secondCurrency.setCurrency("Romanian Leu", "RON");
        } else if (currency2.getValue().toString().equals("Russian Ruble")) {
            secondCurrency.setCurrency("Russian Ruble", "RUB");
        } else if (currency2.getValue().toString().equals("Swedish Krona")) {
            secondCurrency.setCurrency("Swedish Krona", "SEK");
        } else if (currency2.getValue().toString().equals("Singapore Dollar")) {
            secondCurrency.setCurrency("Singapore Dollar", "SGD");
        } else if (currency2.getValue().toString().equals("Thai Baht")) {
            secondCurrency.setCurrency("Thai Baht", "THB");
        } else if (currency2.getValue().toString().equals("Turkish Lira")) {
            secondCurrency.setCurrency("Turkish Lira", "TRY");
        } else if (currency2.getValue().toString().equals("South African Rand")) {
            secondCurrency.setCurrency("South African Rand", "ZAR");
        } else {
            BufferedReader reader = null;
            String line;
            ArrayList<String> lines = new ArrayList<String>(0);
            try {
                reader = new BufferedReader(new FileReader("customCurrencies"));
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                for (int i = 1; i < lines.size(); i += 4) {
                    if (currency2.getValue().toString().equals(lines.get(i))) {
                        secondCurrency.setCurrency(lines.get(i), Double.valueOf(lines.get(i + 1)));
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("IOException 1");
            }
        }

        if (!firstCurrency.isCustomCurrency) {
            DataFetcher.getExchangeRate(firstCurrency);
        }
        if (!secondCurrency.isCustomCurrency) {
            DataFetcher.getExchangeRate(secondCurrency);
        }

        CurrencyCalculation currencyCalculation = new CurrencyCalculation();

        double money = 0.0;

        if (!firstMoneySet.getText().toString().equals("")) {
            try {
                money = Double.parseDouble(firstMoneySet.getText().toString());
            } catch (NumberFormatException e) {

            }

        }

        secondMoneySet.setText(currencyCalculation.exchange(firstCurrency, secondCurrency, money));

    }
}
