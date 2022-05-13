package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.CurrencyScoop;
import model.request.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;


public class CurrencyScoopAPI implements CurrencyScoop {
    private CurrencyRequest currencyReq = new CurrencyRequestOnline();

    public CurrencyScoopAPI(boolean isOnline) {
        if (!isOnline) {
            this.currencyReq = new CurrencyRequestOffline();
        }

    }

    public Convert convert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException {

        return currencyReq.getConvert(from, to, amount);
    }



    public Rate getRate(String from, String to) throws URISyntaxException, IOException, InterruptedException {


        return currencyReq.getRate(from, to);

    }

    public void setRequest(CurrencyRequest currencyReq) {
        this.currencyReq = currencyReq;
    }


}
