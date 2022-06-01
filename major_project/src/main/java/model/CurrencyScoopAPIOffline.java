package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.POJOs.Convert;
import model.POJOs.Rate;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * The offline currencyscoop API implementation.
 */
public class CurrencyScoopAPIOffline implements CurrencyScoop {
    /**
     * Convert convert.
     *
     * @param from   the starting currency code
     * @param to     the ending currency code
     * @param amount the amount to convert
     * @return the Convert object which store the starting and ending currency code and the result of the conversion
     */
    @Override
    public Convert convert(String from, String to, double amount)  {
        String body = "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"timestamp\":1651218496,\"date\":\"2022-04-29\",\"from\":\""+from+"\",\"to\":\""+to+"\",\"amount\":"+amount+",\"value\":0.94720926}}";

        HttpResponse<String> response = new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {

                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double value = jsonObject.getAsJsonObject("response").get("value").getAsDouble();

        return new Convert(value, from, to);
    }

    /**
     * Gets rate.
     *
     * @param from the starting currency code
     * @param to   the ending currency code
     * @return the Rate object which store the starting and ending currency code and the rate of the conversion
     */
    @Override
    public Rate getRate(String from, String to) {
        String s = "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"date\":\"2022-04-29T09:24:51Z\",\"base\":\""+from+"\",\"rates\":{\""+ to +"\":0.94618129}}}";

        HttpResponse<String> response = new HttpResponse<>() {

            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return s;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double rate = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble();
        System.out.println(jsonObject.getAsJsonObject("response").getAsJsonObject("rates"));
        Rate rateObj = new Rate(from, to, rate);

        return rateObj;
    }

    /**
     * Check if API is online mode.
     * In this implementation it always returns false since this is dummy/offline version.
     *
     * @return true if API is online mode, false otherwise
     */
    @Override
    public boolean isOnline() {
        return false;
    }


    public static void main(String[] args) {
        CurrencyScoopAPIOffline c = new CurrencyScoopAPIOffline();
        System.out.println(c.getRate("USD", "EUR").getRate());
    }
}
