package model.online;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.CurrencyScoop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;

public class CurrencyScoopAPI implements CurrencyScoop {
    private static String api_key = "70eefcba3be3480c4a6de8a39cfc9e37";

    public double convert(String from, String to, double amount)  {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&api_key="+api_key))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            double value = jsonObject.getAsJsonObject("response").get("value").getAsDouble();


            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public double getRate(String from, String to)  {

        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/latest?base=" + from + "&symbols=" + to + "&api_key=" + api_key))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            double rate = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble();
            return rate;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }



}
