package model.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Convert;
import model.Rate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyRequestOnline implements CurrencyRequest {
//    private static String api_key = "70eefcba3be3480c4a6de8a39cfc9e37";
    private static String INPUT_API_KEY = System.getenv("INPUT_API_KEY");


    public Convert getConvert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&api_key="+INPUT_API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double value = jsonObject.getAsJsonObject("response").get("value").getAsDouble();
        return new Convert(value, from, to);

    }

    public Rate getRate(String from, String to) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/latest?base=" + from + "&symbols=" + to + "&api_key=" + INPUT_API_KEY))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        JsonElement check = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to);

        if (check.isJsonNull()){

            throw new IllegalArgumentException("Currency not found");
        }
        double rate = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble();
        System.out.println(jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble());

        return new Rate(from, to, rate);

    }




}
