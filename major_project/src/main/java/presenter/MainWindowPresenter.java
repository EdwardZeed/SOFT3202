package presenter;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import view.MainWindowView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class MainWindowPresenter {
    private final MainWindowView mainWindowView;
    private boolean isOnline;
//    private HashMap<String, String> countries = new HashMap<>();
    private CurrencyScoopAPI currencyScoopAPI;
    private PastebinAPI pastebinAPI;

    public MainWindowPresenter(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
    }

    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {
        currencyScoopAPI = new CurrencyScoopAPI(currencyOnline);
        isOnline = currencyOnline;
        pastebinAPI = new PastebinAPI(pastebinOnline);

    }

    public void showMapWindow() {

        Pane root = StageManagement.panes.get("MapWindow");

        Stage stage;
        Scene scene;
        if (root.getScene() != null) {
            scene = root.getScene();
        }
        else{
            scene = new Scene(root);
        }
        if (root.getScene().getWindow() != null) {
            stage = (Stage) root.getScene().getWindow();
        }
        else{
            stage = new Stage();
        }

        stage.setScene(scene);
        stage.show();


//        the following code is modified from https://blog.birost.com/a?ID=00550-7ba27155-686d-4aef-8504-3e5a73dc6ad5
        StageManagement.stages.put("MapWindow", stage);
        StageManagement.controllers.put("MainWindow", this.mainWindowView);


        stage.setOnCloseRequest(event -> {
//            this.mainWindowView.showCurrency();
            this.mainWindowView.rebuildListView(this.currencyScoopAPI.getCountries());
        });

    }

    public void getResult(String fromCurrency, String toCurrency, String amount) throws URISyntaxException, IOException, InterruptedException {
        double amount_num = 0;
        try{
            amount_num = Double.parseDouble(amount);
        }catch (Exception e){
            this.mainWindowView.displayError("Please enter a valid number");
            return;
        }

        if (fromCurrency==null || toCurrency==null){
            this.mainWindowView.displayError("Please select a currency");
            return;
        }
        if (amount_num < 0){
            this.mainWindowView.displayError("Please enter a positive number");
            return;
        }


        double rate;
        double result;
        String fromCountry = currencyScoopAPI.getFromCountry(fromCurrency);
        String toCountry = currencyScoopAPI.getToCountry(toCurrency);
        if (currencyScoopAPI.cacheHit(fromCurrency, toCurrency) && this.isOnline) {
            System.out.println("cache hit");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Currency Converter");
            alert.setContentText("cache hit for this data – use cache, or request fresh data from the API?");
            ButtonType buttonTypeOne = new ButtonType("Use Cache", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeTwo = new ButtonType("Request Fresh Data", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> resultSet = alert.showAndWait();
            if (resultSet.get() == buttonTypeOne) {
                System.out.println("user chose to use cache");
                rate = currencyScoopAPI.getRate(fromCurrency, toCurrency, false).getRate();
                result = currencyScoopAPI.calculateResult(amount_num, rate);
                String output = "from:" + fromCountry + "\nto:" + toCountry + "\nrate:" + rate + "\nstarting value:" + amount +"\nfinishing value:" + result;
                String toOutput = pastebinAPI.createPastin(output).getURI();
                this.mainWindowView.displayResult(rate, result, toOutput);
            }
            else{
                System.out.println("user chose to request fresh data");
                System.out.println(this.mainWindowView);

                Task<Result> task = this.getAPIRequestTask(amount_num, fromCurrency, toCurrency, this.mainWindowView);


                Thread thread = new Thread(task);
                thread.start();


            }
        }
        else{
//                System.out.println("no cache");
            if (this.isOnline) {
                System.out.println("no cache, but online");
                Task<Result> task = this.getAPIRequestTask(amount_num, fromCurrency, toCurrency, this.mainWindowView);
                Thread thread = new Thread(task);
                thread.start();
            }
            else{
                System.out.println("no cache, no online");
                result = currencyScoopAPI.convert(fromCurrency, toCurrency, amount_num).getResult();
                rate = currencyScoopAPI.getRate(fromCurrency, toCurrency, false).getRate();
                String output = "from:" + fromCountry + "\nto:" + toCountry + "\nrate:" + rate + "\nstarting value:" + amount +"\nfinishing value:" + result;
                String toOutput = pastebinAPI.createPastin(output).getURI();
                this.mainWindowView.displayResult(rate, result, toOutput);
            }

        }
    }

    public Task<Result> getAPIRequestTask(double finalAmount, String finalFromCurrency, String finalToCurrency, MainWindowView mainWindowView){
        Task<Result> task = new Task<>() {
            @Override
            protected Result call() {
                mainWindowView.disableConvert();
                System.out.println(Thread.currentThread().getName());
                System.out.println(currencyScoopAPI == null);
                mainWindowView.setProgressIndicator(true);
                System.out.println(Thread.currentThread().getName());

                Rate rate;
                Convert result;
                try {
                    rate = currencyScoopAPI.getRate(finalFromCurrency, finalToCurrency, true);
                    result = currencyScoopAPI.convert(finalFromCurrency, finalToCurrency, finalAmount);
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Result message = new Result(rate, result);

                this.updateValue(message);
                return message;

            }
        };
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("task value changed");

            Rate rate = newValue.getRate();
            Convert result = newValue.getConvertedResult();
            String from = currencyScoopAPI.getFromCountry(rate.getFrom());
            String to = currencyScoopAPI.getToCountry(rate.getTo());

            String output = "from:" + from + "\nto:" + to + "\nrate:" + rate + "\nstarting value:" + finalAmount +"\nfinishing value:" + result;
            String toOutput = pastebinAPI.createPastin(output).getURI();
            mainWindowView.setProgressIndicator(false);
            mainWindowView.displayResult(rate.getRate(), result.getResult(), toOutput);
            mainWindowView.enableConvert();

        });

        return task;
    }


    public void addCountry(String countryName, String currencyCode) {
        this.currencyScoopAPI.addCountry(countryName, currencyCode);
        this.mainWindowView.rebuildListView(this.currencyScoopAPI.getCountries());
    }

    public void clearCache(){
        this.currencyScoopAPI.clearCache();
    }

    public void clear() {
        this.currencyScoopAPI.clear();
//        this.mainWindowView.updateListView(this.currencyScoopAPI.getCountries());
        this.mainWindowView.rebuildListView(this.currencyScoopAPI.getCountries());
    }

    public void remove(String currencyCode) {
        this.currencyScoopAPI.remove(currencyCode);
//        this.mainWindowView.updateListView(this.currencyScoopAPI.getCountries());
        this.mainWindowView.rebuildListView(this.currencyScoopAPI.getCountries());
    }
}
