package model;

import model.POJOs.Convert;
import model.POJOs.Rate;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The interface Currency scoop.
 */
public interface CurrencyScoop {
    /**
     * Request currencyscoop API to get the conversion result between two currencies.
     *
     * @param from   the starting currency code
     * @param to     the ending currency code
     * @param amount the amount to convert
     * @return the Convert object which store the starting and ending currency code and the result of the conversion
     * @throws URISyntaxException       the uri syntax exception
     * @throws IOException              the io exception
     * @throws InterruptedException     the interrupted exception
     * @throws IllegalArgumentException if the currency code is not valid
     */
    public Convert convert(String from, String to, double amount)throws URISyntaxException, IOException, InterruptedException, IllegalArgumentException;

    /**
     * Request currencyscoop API to get the conversion rate between two currencies.
     *
     * @param from the starting currency code
     * @param to   the ending currency code
     * @return the Rate object which store the starting and ending currency code and the rate of the conversion
     * @throws URISyntaxException       the uri syntax exception
     * @throws IOException              the io exception
     * @throws InterruptedException     the interrupted exception
     * @throws IllegalArgumentException if the currency code is not valid
     */
    public Rate getRate(String from, String to)throws URISyntaxException, IOException, InterruptedException, IllegalArgumentException;

    /**
     * Check if API is online mode.
     *
     * @return true if API is online mode, false otherwise
     */
    public boolean isOnline();
}
