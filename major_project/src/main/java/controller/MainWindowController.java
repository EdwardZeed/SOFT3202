package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.CurrencyScoopAPI;
import model.PastebinAPI;
import view.MainWIndowView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MainWindowController {
    private MainWIndowView mainWindowView;
    private HashMap<String, String> countries = new HashMap<>();
    private CurrencyScoopAPI currencyScoopAPI;
    private PastebinAPI pastebinAPI;
    public MainWindowController(MainWIndowView mainWIndowView) {
        this.mainWindowView = mainWIndowView;
    }

    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {
        currencyScoopAPI = new CurrencyScoopAPI(currencyOnline);
        pastebinAPI = new PastebinAPI(pastebinOnline);
    }

    public void showMapWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MapWindow.fxml"));
        Pane root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

//        the following code is modified from https://blog.birost.com/a?ID=00550-7ba27155-686d-4aef-8504-3e5a73dc6ad5
        StageManagement.stages.put("MapWindow", stage);
        StageManagement.controllers.put("MainWindow", this.mainWindowView);

        stage.setOnCloseRequest(event -> {
            this.mainWindowView.showCurrency();
        });
    }

    public void getResult(String fromCurrency, String toCurrency, double amount) throws URISyntaxException, IOException, InterruptedException {
        double result = currencyScoopAPI.convert(fromCurrency, toCurrency, amount).getResult();
        double rate = currencyScoopAPI.getRate(fromCurrency, toCurrency).getRate();

        //        get the country name from the currency code
        String fromCountry = "";
        String toCountry = "";
        for (String key : countries.keySet()) {
            if (countries.get(key).equals(fromCurrency)) {
                fromCountry = key;
            }
            if (countries.get(key).equals(toCurrency)) {
                toCountry = key;
            }
        }
        String output = "from:" + fromCountry + "\nto:" + toCountry + "\nrate:" + rate + "\nstarting value:" + amount +"\nfinishing value:" + result;
        String toOutput = pastebinAPI.createPastin(output).getURI();
        System.out.println(toOutput);

        this.mainWindowView.displayResult(result, rate, toOutput);
    }

    public void addCountry(String countryName, String currencyCode) {
        if (!countries.containsKey(countryName)) {
            countries.put(countryName, currencyCode);
        }
        this.mainWindowView.updateListView(this.countries);
//        this.countries.put(countryName, currencyCode);
    }

    public void clear() {
        this.countries.clear();
        this.mainWindowView.updateListView(this.countries);
    }

    public void remove(String currencyCode) {
        for (String key : countries.keySet()) {
            if (countries.get(key).equals(currencyCode)) {
                countries.remove(key);
                this.mainWindowView.updateListView(this.countries);
            }
        }
    }
}
