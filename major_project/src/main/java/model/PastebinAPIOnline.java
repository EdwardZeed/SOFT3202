package model;


import model.POJOs.PastebinResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The online pastebin API implementation.
 */
public class PastebinAPIOnline implements Pastebin {

    private static String PASTEBIN_API_KEY = System.getenv("PASTEBIN_API_KEY");

    /**
     * Instantiates a new Pastebin api online.
     */
    public PastebinAPIOnline() {

    }

    /**
     * Post content to pastebin and return the pastebin url.
     *
     * @param text the text to post
     * @return the pastebin result stores the url of the pastebin
     */
    public PastebinResult createPastin(String text)  {

        try {
            System.out.println("Posting to Pastebin");
            HttpRequest request = HttpRequest.newBuilder(new URI("https://pastebin.com/api/api_post.php"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("api_dev_key="+PASTEBIN_API_KEY+"&api_paste_code="+text+"&api_option=paste&api_paste_format=text"))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("post successfully");

            return new PastebinResult(response.body());
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }



}
