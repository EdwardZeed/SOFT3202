package presenter;

import javafx.concurrent.Task;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.*;
import view.MainWindowView;
import view.MapView;

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

        mapStage.setOnCloseRequest(event -> this.mainWindowView.rebuildListView(this.currencyScoopAPI.getCountries()));

    }

    public void getResult(String fromCurrency, String toCurrency, String amount) throws URISyntaxException, IOException, InterruptedException {
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


        double rate;
        double result;
        String fromCountry = currencyScoopAPI.getFromCountry(fromCurrency);
        String toCountry = currencyScoopAPI.getToCountry(toCurrency);
        if (currencyScoopAPI.cacheHit(fromCurrency, toCurrency) && this.isOnline) {
            System.out.println("cache hit");
            Optional<ButtonType> resultSet = this.mainWindowView.displayCacheHit();

            if (resultSet.get().getButtonData().getTypeCode().equals("Y")) {
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

                Rate rate = null;
                Convert result = null;
                try {
                    rate = currencyScoopAPI.getRate(finalFromCurrency, finalToCurrency, true);
                    result = currencyScoopAPI.convert(finalFromCurrency, finalToCurrency, finalAmount);
                } catch (URISyntaxException | IOException | InterruptedException | IllegalArgumentException e) {
                    mainWindowView.displayError("invalid currency");
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

    public void controlBGM(MediaPlayer.Status status) {
        if(status == MediaPlayer.Status.PLAYING){
            this.mainWindowView.pause();
        }else{
            this.mainWindowView.play();
        }
    }


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
