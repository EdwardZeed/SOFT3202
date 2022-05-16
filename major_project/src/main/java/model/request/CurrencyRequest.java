package model.request;

import model.Convert;
import model.Rate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public interface CurrencyRequest {
    public Convert getConvert(String from, String to, double amount) throws URISyntaxException, IOException, InterruptedException;
    public Rate getRate(String from, String to) throws URISyntaxException, IOException, InterruptedException;

}
