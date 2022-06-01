package model;

import model.POJOs.Convert;
import model.POJOs.PastebinResult;
import model.POJOs.Rate;

import java.util.Currency;
import java.util.HashMap;

/**
 * Model object handling the data and API requests.
 */
public class Model {
    private CurrencyScoop currencyScoop;
    private Pastebin pastebin;
    private Database db;

    private HashMap<String, String> countries = new HashMap<>();

    /**
     * Instantiates a new Model.
     *
     * @param currencyScoop the currencyscoop(online or offline version)
     * @param pastebin      the pastebin(online or offline version)
     */
    public Model(CurrencyScoop currencyScoop, Pastebin pastebin) {
        this.currencyScoop = currencyScoop;
        this.pastebin = pastebin;
        this.db = new Database();
    }

    /**
     * Gets convert from API.
     *
     * @param from   the start currency code
     * @param to     the end currency code
     * @param amount the amount
     * @return the convert that stores the start currency code, end currency code, the conversion result.
     * @throws IllegalArgumentException when the given currency code is not valid
     */
    public Convert getConvert(String from, String to, double amount) {
        if (!Currency.getAvailableCurrencies().contains(Currency.getInstance(from)) || !Currency.getAvailableCurrencies().contains(Currency.getInstance(to))) {
            System.out.println("Invalid currency");
            throw new IllegalArgumentException("Invalid currency code");
        }

        try{
            Convert convert = currencyScoop.convert(from, to, amount);
            return convert;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Gets rate. If useCache is true, then it will get the rate from the database. Otherwise, it will get the rate from the API and store as cache.
     *
     * @param from     the start currency code
     * @param to       the end currency code
     * @param useCache the use cache or not
     * @return the rate
     * @throws IllegalArgumentException when the given currency code is not valid
     */
    public Rate getRate(String from, String to, boolean useCache) {
        if (!Currency.getAvailableCurrencies().contains(Currency.getInstance(from)) || !Currency.getAvailableCurrencies().contains(Currency.getInstance(to))) {
            System.out.println("Invalid currency");
            throw new IllegalArgumentException("Invalid currency code");
        }
        if (useCache) {
            double rate = db.getRate(from, to);
            return new Rate(from, to, rate);
        }
        try{
            Rate rate = currencyScoop.getRate(from, to);
            return rate;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Post to pastebin pastebin result.
     *
     * @param text the text to post to pastebin
     * @return the PastebinResult object that contains the url of the pastebin
     */
    public PastebinResult postToPastebin(String text) {
        try{
            PastebinResult result = pastebin.createPastin(text);
            return result;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Store the user selected country.
     *
     * @param countryName  the country name
     * @param currencyCode the currency code
     */
    public void addCountry(String countryName, String currencyCode) {
        if (!countries.containsKey(countryName)) {
            countries.put(countryName, currencyCode);
        }

    }

    /**
     * Remove a country from HashMap.
     *
     * @param currencyCode the currency code
     */
    public void remove(String currencyCode) {

        this.countries.remove(currencyCode);

    }

    /**
     * Clear all the stored countries.
     */
    public void clear() {
        this.countries.clear();

    }

    /**
     * Gets countries.
     *
     * @return the HashMap that stores the country name and currency code
     */
    public HashMap<String, String> getCountries() {
        return countries;
    }

    /**
     * Get the country name of the given currency code.
     *
     * @param currency the currency code
     * @return the country name of the given currency code
     */
    public String getCountryName(String currency){
        String country = "";
        for (String key : countries.keySet()) {
            if (countries.get(key).equals(currency)) {
                country = key;
            }
        }
        return country;
    }


    /**
     * Check whether there is a record in database for given from and to currency code.
     *
     * @param from the start currency code
     * @param to   the end currency code
     * @return the boolean
     */
    public boolean cacheHit(String from, String to){
        return currencyScoop.isOnline() && db.getRate(from, to) != 0;
    }

    /**
     * Calculate result convert.
     *
     * @param amount the amount
     * @param rate   the rate
     * @return the Convert object that stores the result conversion
     */
    public Convert calculateResult(double amount, Rate rate) {
        return new Convert(amount*rate.getRate(), rate.getFrom(), rate.getTo());
    }

    /**
     * Clear exist cache in databse.
     */
    public void clearCache() {
        db.clear();
    }

    /**
     * Set input api.
     *
     * @param currencyScoop the CurrencyScoop object
     */
    public void setInputAPI(CurrencyScoop currencyScoop){
        this.currencyScoop = currencyScoop;
    }
}
