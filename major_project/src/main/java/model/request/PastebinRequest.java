package model.request;

import java.net.http.HttpResponse;

public interface PastebinRequest {
    public HttpResponse<String> getPastebinResponse(String text);
}
