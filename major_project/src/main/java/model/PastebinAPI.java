package model;



import model.Pastebin;
import model.request.*;

import java.net.http.HttpResponse;

public class PastebinAPI implements Pastebin {
    private PastebinRequest request = new PastebinRequestOnline();

    public PastebinAPI(boolean isOnline) {
        if (!isOnline) {
            this.request = new PastebinRequestOffline();
        }
    }


    public String createPastin(String text)  {
        HttpResponse<String> response = request.getPastebinResponse(text);
        return response.body();

    }


    public void setRequest(PastebinRequest request) {
        this.request = request;
    }
}
