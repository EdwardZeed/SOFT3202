package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.CurrencyScoop;
import model.request.*;

import java.net.http.HttpResponse;


public class CurrencyScoopAPI implements CurrencyScoop {
    private CurrencyRequest currencyReq = new CurrencyRequestOnline();

    public CurrencyScoopAPI(boolean isOnline) {
        if (!isOnline) {
            this.currencyReq = new CurrencyRequestOffline();
        }

    }

    public double convert(String from, String to, double amount)  {

        HttpResponse<String> response = currencyReq.getConvertResponse(from, to, amount);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double value = jsonObject.getAsJsonObject("response").get("value").getAsDouble();
        return value;
    }



    public double getRate(String from, String to)  {

        HttpResponse<String> response = currencyReq.getRateResponse(from, to);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        double rate = jsonObject.getAsJsonObject("response").getAsJsonObject("rates").get(to).getAsDouble();
        System.out.println(jsonObject.getAsJsonObject("response").getAsJsonObject("rates"));
        return rate;

    }

    public void setRequest(CurrencyRequest currencyReq) {
        this.currencyReq = currencyReq;
    }
}
