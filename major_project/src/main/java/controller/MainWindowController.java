package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.CurrencyScoop;
import model.Pastebin;
import model.CurrencyScoopAPI;
import model.PastebinAPI;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MainWindowController {
    @FXML
    private ListView listView;
    @FXML
    private Button AddCurrencyBtn, clearBtn, removeBtn;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox from, to;

    private HashMap<String, String> countries = new HashMap<>();
    private CurrencyScoop api;
    private Pastebin pastebin;


    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {
        api = new CurrencyScoopAPI(currencyOnline);
        pastebin = new PastebinAPI(pastebinOnline);
    }

    public void AddCurrency(){
        System.out.println(this);
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

        StageManagement.stages.put("MapWindow", stage);
        StageManagement.controllers.put("MainWindow", this);

        stage.setOnCloseRequest(event -> {
            showCurrency();
        });


    }

    public void addCurrencyToListView(String currencyCode, String countryName) {

        listView.getItems().add(countryName + "\n" + currencyCode);

    }

    public boolean checkEleInListView(String currencyCode, String countryName) {
        for (Object ele : listView.getItems()) {
            if (ele.equals(countryName + "\n" + currencyCode)) {
                return true;
            }
        }
        return false;
    }


    public void addCountry(String countryName, String currencyCode) {
        if (!countries.containsKey(countryName)) {
            countries.put(countryName, currencyCode);
        }
//        this.countries.put(countryName, currencyCode);
    }

    public void showCurrency(){
        countries.forEach((k,v) -> {
            if (!checkEleInListView(v, k)) {
                addCurrencyToListView(v,k);
                from.getItems().add(v);
                to.getItems().add(v);
            }
        });

    }

    public void handleConvert() throws IOException, URISyntaxException, InterruptedException {
        String fromCurrency = null;
        String toCurrency = null;
        double amount = 0;

        boolean isValid = false;
        try {
            fromCurrency = from.getSelectionModel().getSelectedItem().toString();
            toCurrency = to.getSelectionModel().getSelectedItem().toString();
            amount = Double.parseDouble(this.amount.getText());
            isValid = true;
        }catch (Exception e){
            Alert invalid = new Alert(Alert.AlertType.ERROR);
            invalid.setTitle("Invalid Input");
            invalid.setHeaderText("Invalid Input");
            invalid.setContentText("Please enter a valid input");
            invalid.showAndWait();
        }
        if (isValid) {
            double result = api.convert(fromCurrency, toCurrency, amount);
            double rate = api.getRate(fromCurrency, toCurrency);

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
            String toOutput = pastebin.createPastin(output);
            System.out.println(toOutput);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setHeaderText("Result");
            alert.setContentText("Result: " + result + "\n" + "Rate: " + rate + "\n" + "output: " + toOutput +
                    "\n" + "click show detail to browse the output website");

    //        make the output link clickable
            Hyperlink link = new Hyperlink(toOutput);
            link.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI(toOutput));
                }
                catch (Exception e){
                    System.out.println(e);
                }
            });

            alert.getDialogPane().setExpandableContent(link);
            alert.showAndWait();
        }

    }

    public void handleClear(){
        listView.getItems().clear();
        from.getItems().clear();
        to.getItems().clear();
    }

    public void handleRemove(){
        String selected = listView.getSelectionModel().getSelectedItem().toString();
        String currencyCode = selected.substring(selected.indexOf("\n") + 1);
        listView.getItems().remove(selected);
        from.getItems().remove(currencyCode);
        to.getItems().remove(currencyCode);
    }
}




