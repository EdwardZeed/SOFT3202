package model.request;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class CurrencyRequestOffline implements CurrencyRequest {
    public HttpResponse<String> getConvertResponse(String from, String to, double amount) {
        String body = "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"timestamp\":1651218496,\"date\":\"2022-04-29\",\"from\":\""+from+"\",\"to\":\""+to+"\",\"amount\":"+amount+",\"value\":0.94720926}}";

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

                return body;
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
        return response;
    }

    public HttpResponse<String> getRateResponse(String from, String to) {
        String s = "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"date\":\"2022-04-29T09:24:51Z\",\"base\":\""+from+"\",\"rates\":{\""+ to +"\":0.94618129}}}";

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
                 return s;
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
        return response;
    }


}
