package model;


import model.request.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;


public class CurrencyScoopAPI {
    private CurrencyRequest currencyReq = new CurrencyRequestOnline();
    private Database db;
    private boolean isOnline;
    private HashMap<String, String> countries = new HashMap<>();

    public CurrencyScoopAPI(boolean isOnline) {
        this.isOnline = isOnline;

        if (!isOnline) {
            this.currencyReq = new CurrencyRequestOffline();
        }
        if (isOnline) {
            this.db = new Database();
        }

    }

    public Convert convert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException {
        Convert result = currencyReq.getConvert(from, to, amount);

        return result;
    }

    public double calculateResult(double amount, double rate) {
        return amount * rate;
    }



    public Rate getRate(String from, String to, boolean update) throws URISyntaxException, IOException, InterruptedException {
        double fromDB = db.getRate(from, to);

        if (update || fromDB == 0) {
            Rate fromAPI = currencyReq.getRate(from, to);

            if (this.isOnline) {
                db.addConversation(from, to, fromAPI.getRate());
            }
            return fromAPI;
        }


        return new Rate(from, to, fromDB);

    }

    public boolean cacheHit(String from, String to) {
        return db.getRate(from, to) != 0;
    }

    public void clearCache(){
        db.clear();
    }


    public void setRequest(CurrencyRequest currencyReq) {
        this.currencyReq = currencyReq;
    }

    public void addCountry(String countryName, String currencyCode) {
        if (!countries.containsKey(countryName)) {
            countries.put(countryName, currencyCode);
        }

    }

    public void remove(String currencyCode) {

        this.countries.remove(currencyCode);

    }

    public void clear() {
        this.countries.clear();

    }

    public HashMap<String, String> getCountries() {
        return countries;
    }

    public String getFromCountry(String currency){
        String country = "";
        for (String key : countries.keySet()) {
            if (countries.get(key).equals(currency)) {
                country = key;
            }
        }
        return country;
    }

    public String getToCountry(String currency){
        String country = "";
        for (String key : countries.keySet()) {
            if (countries.get(key).equals(currency)) {
                country = key;
            }
        }
        return country;
    }
}
