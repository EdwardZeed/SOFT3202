package model.request;

import java.net.http.HttpResponse;

public interface CurrencyRequest {
    public HttpResponse<String> getConvertResponse(String from, String to, double amount);
    public HttpResponse<String> getRateResponse(String from, String to);

}
