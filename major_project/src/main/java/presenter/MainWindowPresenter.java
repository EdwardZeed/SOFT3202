package presenter;

import javafx.concurrent.Task;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.*;
import model.POJOs.Convert;
import model.POJOs.Rate;
import model.POJOs.Result;
import view.MainWindowView;
import view.MapView;

import java.util.Optional;

/**
 * The mediator between the view and the model. Make all the decisions for view.
 */
public class MainWindowPresenter {
    private final MainWindowView mainWindowView;
    private Model model;

    /**
     * Instantiates a new Main window presenter.
     *
     * @param mainWindowView the main window view
     */
    public MainWindowPresenter(MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
    }

    /**
     * Sets status.
     * If currencyOnline is true, then use online implementation for CurrencyScoop, else use offline implementation.
     * If pastebinOnline is true, then use online implementation for Pastebin, else use offline implementation.
     *
     * @param currencyOnline the currency online
     * @param pastebinOnline the pastebin online
     */
    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {

        CurrencyScoop input;
        Pastebin output;
        if (currencyOnline) {
            input = new CurrencyScoopAPIOnline();
        }
        else {
            input = new CurrencyScoopAPIOffline();
        }
        if (pastebinOnline) {
            output = new PastebinAPIOnline();
        }
        else {
            output = new PastebinAPIOffline();
        }
        model = new Model(input, output);
    }

    /**
     * Show map window. This method will also show all the selected countries' currency code after closing.
     */
    public void showMapWindow() {

//        the following code is modified from https://blog.birost.com/a?ID=00550-7ba27155-686d-4aef-8504-3e5a73dc6ad5

        StageManagement.views.put("MainWindow", this.mainWindowView);

        if (StageManagement.stages.get("MapStage") == null) {
            MapView mapView = (MapView) StageManagement.views.get("MapWindow");
            mapView.buildMap();
        }
        else{
            MapView mapView = (MapView) StageManagement.views.get("MapWindow");
            mapView.rebuildMap();
        }

        Stage mapStage = StageManagement.stages.get("MapStage");

        mapStage.setOnCloseRequest(event -> this.mainWindowView.rebuildListView(this.model.getCountries()));

    }

    /**
     * Show all the selected countries' currency code in main window.
     */
    public void showCurrency(){
        this.mainWindowView.rebuildListView(this.model.getCountries());
    }

    /**
     * Gets result of the conversion between the two given currency codes.
     * If there is a cache hit, the result will depend on the user's choice. If user choose to use cache, then the result is pulled from database. If user choose to request fresh data, then this method will proceed to request fresh data from API.
     * If there is no cache hit, then this method will proceed to request fresh data from API.
     * This method involves concurrency.
     *
     * @param fromCurrency the from currency
     * @param toCurrency   the to currency
     * @param amount       the amount
     */
    public void getResult(String fromCurrency, String toCurrency, String amount)  {
        double amount_num;
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


        if (model.cacheHit(fromCurrency, toCurrency)) {
            System.out.println("cache hit");
            Optional<ButtonType> resultSet = this.mainWindowView.displayCacheHit();
            System.out.println(resultSet.get());
            if (resultSet.get().getButtonData().getTypeCode().equals("Y")) {
                System.out.println("user chose to use cache");
//                rate = currencyScoopAPI.getRate(fromCurrency, toCurrency, false).getRate();
//                result = currencyScoopAPI.calculateResult(amount_num, rate);
//                String output = "from:" + fromCountry + "\nto:" + toCountry + "\nrate:" + rate + "\nstarting value:" + amount +"\nfinishing value:" + result;
//                String toOutput = pastebinAPI.createPastin(output).getURI();
//                this.mainWindowView.displayResult(rate, result);
                Task<Result> task = this.getAPIRequestTask(amount_num,fromCurrency,toCurrency,this.mainWindowView,true);
                Thread thread = new Thread(task);
                thread.start();
            }
            else if (resultSet.get().getButtonData().getTypeCode().equals("N")) {
                System.out.println("user chose to request fresh data");

                Task<Result> task = this.getAPIRequestTask(amount_num, fromCurrency, toCurrency, this.mainWindowView, false);

                Thread thread = new Thread(task);
                thread.start();

            }
        }
        else{
            System.out.println("no cache");
            Task<Result> task = this.getAPIRequestTask(amount_num, fromCurrency, toCurrency, this.mainWindowView, false);
            Thread thread = new Thread(task);
            thread.start();

        }
    }

    /**
     * Create the unique task to not occupy the GUI thread.
     * if useCache is true, then the rate will be pulled from cache and the result will be calculated by model instead of API.
     *
     * @param finalAmount       the amount to be converted
     * @param finalFromCurrency the start currency code
     * @param finalToCurrency   the end currency code
     * @param mainWindowView    the MainWindowView
     * @param useCache          boolean that determines if the cache should be used
     * @return the task to handle the API request or the cache
     */
    public Task<Result> getAPIRequestTask(double finalAmount, String finalFromCurrency, String finalToCurrency, MainWindowView mainWindowView, boolean useCache){
        Task<Result> task = new Task<>() {
            @Override
            protected Result call() {
                mainWindowView.disableConvert();
                mainWindowView.setProgressIndicator(true);
                System.out.println(Thread.currentThread().getName());

                Rate rate = null;
                Convert result = null;
                try {
                    rate = model.getRate(finalFromCurrency, finalToCurrency, useCache);
                    if (useCache){
                        result = model.calculateResult(finalAmount, rate);
                    }
                    else {
                        result = model.getConvert(finalFromCurrency, finalToCurrency, finalAmount);
                    }
                } catch (IllegalArgumentException e) {
                    mainWindowView.displayError("invalid currency");
                }
                mainWindowView.setProgressIndicator(false);
                Result message = new Result(rate, result);

                this.updateValue(message);
                return message;

            }
        };
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("task value changed");

            Rate rate = newValue.getRate();
            Convert result = newValue.getConvertedResult();
            String from = model.getCountryName(rate.getFrom());
            String to = model.getCountryName(rate.getTo());

            String output = "from:" + from + "\nto:" + to + "\nrate:" + rate.getRate() + "\nstarting value:" + finalAmount +"\nfinishing value:" + result.getResult();

            Optional<ButtonType> generateReport = mainWindowView.displayResult(rate.getRate(), result.getResult());
            if (generateReport.isPresent() && generateReport.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
                mainWindowView.setProgressIndicator(true);
                String toOutput = model.postToPastebin(output).getURI();
                mainWindowView.setProgressIndicator(false);
                mainWindowView.displayPastebin(toOutput);
            }
//            mainWindowView.setProgressIndicator(false);
            mainWindowView.enableConvert();

        });

        return task;
    }


    /**
     * Add country to model and rebuild the main window to show all the currency code information.
     *
     * @param countryName  the country name
     * @param currencyCode the currency code
     */
    public void addCountry(String countryName, String currencyCode) {
        this.model.addCountry(countryName, currencyCode);
        this.mainWindowView.rebuildListView(this.model.getCountries());
    }

    /**
     * Clear cache.
     */
    public void clearCache(){
        this.model.clearCache();
    }

    /**
     * Clear all the selected currency codes and refresh the main window.
     */
    public void clear() {
        this.model.clear();
//        this.mainWindowView.updateListView(this.currencyScoopAPI.getCountries());
        this.mainWindowView.rebuildListView(this.model.getCountries());
    }

    /**
     * Remove a selected currency code and refresh the main window.
     *
     * @param currencyCode the currency code
     */
    public void remove(String currencyCode) {
        this.model.remove(currencyCode);
//        this.mainWindowView.updateListView(this.currencyScoopAPI.getCountries());
        this.mainWindowView.rebuildListView(this.model.getCountries());
    }

    /**
     * Control theme song. If the theme song is playing, pause it. Otherwise, play it.
     *
     * @param status the status
     */
    public void controlBGM(MediaPlayer.Status status) {
        if(status == MediaPlayer.Status.PLAYING){
            this.mainWindowView.pause();
        }else{
            this.mainWindowView.play();
        }
    }


    /**
     * Control mode. If the mode is dark, change to light mode. Otherwise, change to dark mode.
     *
     * @param isLight the is light
     */
    public void controlMode(boolean isLight) {
        isLight = !isLight;
        this.mainWindowView.setMode(isLight);
        if (isLight) {
            this.mainWindowView.setLightMode();
        }
        else{
            this.mainWindowView.setDarkMode();
        }
    }

}
