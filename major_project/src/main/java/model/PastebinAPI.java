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


    public PastebinResult createPastin(String text)  {
        PastebinResult result = request.getPastebinResponse(text);
        return result;

    }


    public void setRequest(PastebinRequest request) {
        this.request = request;
    }
}
