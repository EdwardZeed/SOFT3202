package view;

import controller.MainWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.CurrencyScoop;
import model.Pastebin;
import model.CurrencyScoopAPI;
import model.PastebinAPI;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class MainWIndowView {
    @FXML
    private ListView<String> listView;
    @FXML
    private Button AddCurrencyBtn, clearBtn, removeBtn;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox<String> from, to;

    private HashMap<String, String> countries = new HashMap<>();
    private CurrencyScoop api;
    private Pastebin pastebin;
    private MainWindowController mainWindowController;

    public void initialize() {
        mainWindowController = new MainWindowController(this);
    }


    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {
//        api = new CurrencyScoopAPI(currencyOnline);
//        pastebin = new PastebinAPI(pastebinOnline);
        this.mainWindowController.setStatus(currencyOnline, pastebinOnline);
    }

    public MainWindowController getController() {
        return mainWindowController;
    }

    public void AddCurrency(){
        this.mainWindowController.showMapWindow();
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
            try{
                this.mainWindowController.getResult(fromCurrency, toCurrency, amount);
            }
            catch (Exception e){
                displayError(e.getMessage());
            }
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



    public void addCurrencyToListView(String currencyCode, String countryName) {

        listView.getItems().add(countryName + "\n" + currencyCode);

    }

    public void updateListView(HashMap<String, String> newCountries) {
        this.countries = newCountries;

    }

    public boolean checkEleInListView(String currencyCode, String countryName) {
        for (Object ele : listView.getItems()) {
            if (ele.equals(countryName + "\n" + currencyCode)) {
                return true;
            }
        }
        return false;
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




    public void displayResult(double result, double rate, String toOutput) {
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

    public void displayError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }


}




