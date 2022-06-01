package model;

import model.POJOs.PastebinResult;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * The offline pastebin API request implementation.
 */
public class PastebinAPIOffline implements Pastebin{
    /**
     * Post content to pastebin and return the pastebin url.
     *
     * @param text the text to post
     * @return the pastebin result stores the url of the pastebin
     */
    @Override
    public PastebinResult createPastin(String text) {
        HttpResponse<String> response = new HttpResponse<>() {

            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return "https://pastebin.com/0z5GEYtM";
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
        return new PastebinResult(response.body());
    }
}
