package model.request;

import model.PastebinResult;

import java.net.http.HttpResponse;

public interface PastebinRequest {
    public PastebinResult getPastebinResponse(String text);
}
