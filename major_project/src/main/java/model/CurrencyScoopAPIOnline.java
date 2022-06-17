package model;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.POJOs.Convert;
import model.POJOs.Rate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;


/**
 * Online currencyscoop API implementation
 */
public class CurrencyScoopAPIOnline implements CurrencyScoop {
    private Database db;
    private static String INPUT_API_KEY = System.getenv("INPUT_API_KEY");

    /**
     * Instantiates a new Currency scoop api online.
     */
    public CurrencyScoopAPIOnline() {
        this.db = new Database();

    }

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
    public Convert convert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException, IllegalArgumentException {
        System.out.println("Converting from API");
        HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&api_key="+INPUT_API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double value = jsonObject.getAsJsonObject("response").get("value").getAsDouble();
        System.out.println("convert from API successfully");

        Convert result = new Convert(value, from, to);

        return result;
    }


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
    public Rate getRate(String from, String to) throws URISyntaxException, IOException, InterruptedException, IllegalArgumentException {
        if (!Currency.getAvailableCurrencies().contains(Currency.getInstance(from)) || !Currency.getAvailableCurrencies().contains(Currency.getInstance(to))) {
            System.out.println("Invalid currency");
            throw new IllegalArgumentException("Invalid currency code");
        }
        System.out.println("Getting rate from API");
        HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/latest?base=" + from + "&symbols=" + to + "&api_key=" + INPUT_API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        JsonElement check = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to);
        System.out.println("in API");
        System.out.println(response == null);
        if (check.isJsonNull()){

            throw new IllegalArgumentException("Currency not found");
        }

        double rate = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble();
        System.out.println(jsonObject.getAsJsonObject("response").getAsJsonObject("rates"));
        Rate rateObj = new Rate(from, to, rate);
        System.out.println("get from API successfully");

        this.db.addConversation(from, to, rate);

        return rateObj;

    }

    /**
     * Check if API is online mode.
     * In this implementation it always returns true since it's online version.
     *
     * @return true if API is online mode, false otherwise
     */
    @Override
    public boolean isOnline() {
        return true;
    }

}
