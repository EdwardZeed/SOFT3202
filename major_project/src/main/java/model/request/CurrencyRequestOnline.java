package model.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyRequestOnline implements CurrencyRequest {
    private static String api_key = "70eefcba3be3480c4a6de8a39cfc9e37";


    public HttpResponse<String> getConvertResponse(String from, String to, double amount)  {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&api_key="+api_key))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResponse<String> getRateResponse(String from, String to)  {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.currencyscoop.com/v1/latest?base=" + from + "&symbols=" + to + "&api_key=" + api_key))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CurrencyRequestOnline request = new CurrencyRequestOnline();
        HttpResponse<String> response = request.getRateResponse("USD", "EUR");
        System.out.println(response.body());
    }



}
