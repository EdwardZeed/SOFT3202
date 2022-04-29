package model.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PastebinRequestOnline implements PastebinRequest {
    private static String api_dev_key = "j5l8W7SgCxRRS7PNxWV8g6j8KX7m43x0";
    public HttpResponse<String> getPastebinResponse(String text)  {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://pastebin.com/api/api_post.php"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("api_dev_key="+api_dev_key+"&api_paste_code="+text+"&api_option=paste&api_paste_format=text"))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
}
