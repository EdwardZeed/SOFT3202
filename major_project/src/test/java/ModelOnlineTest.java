import model.CurrencyScoopAPI;
import model.PastebinAPI;
import model.request.*;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ModelOnlineTest {
    @Test
    public void testConvertOnline() {
        CurrencyRequestOnline mockRequestOnline = mock(CurrencyRequestOnline.class);
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
                String body = "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"timestamp\":1651218496,\"date\":\"2022-04-29\",\"from\":\"USD\",\"to\":\"EUR\",\"amount\":1,\"value\":0.94720926}}";
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
        when(mockRequestOnline.getConvertResponse(anyString(), anyString(), anyDouble())).thenReturn(response);
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        api.setRequest(mockRequestOnline);

        assertEquals(api.convert("USD", "EUR", 1), 0.94720926);
        verify(mockRequestOnline, times(1)).getConvertResponse(anyString(), anyString(), anyDouble());
    }

    @Test
    public void testConvertOffline(){
        CurrencyRequestOffline mockRequestOnline = mock(CurrencyRequestOffline.class);
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
                return "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"timestamp\":1651218496,\"date\":\"2022-04-29\",\"from\":\""+"USD"+"\",\"to\":\""+"EUR"+"\",\"amount\":1,\"value\":0.94720926}}";
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
        when(mockRequestOnline.getConvertResponse(anyString(), anyString(), anyDouble())).thenReturn(response);

        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOnline);

        assertEquals(api.convert("USD", "EUR", 1), 0.94720926);
        verify(mockRequestOnline, times(1)).getConvertResponse(anyString(), anyString(), anyDouble());
    }

    @Test
    public void testRateOnline() {
        CurrencyRequestOnline mockRequestOnline = mock(CurrencyRequestOnline.class);
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
                return "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"date\":\"2022-04-29T08:29:48Z\",\"base\":\"USD\",\"rates\":{\"EUR\":0.9463667}}}";
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

        when(mockRequestOnline.getRateResponse(anyString(), anyString())).thenReturn(response);
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        api.setRequest(mockRequestOnline);

        assertEquals(api.getRate("USD", "EUR"), 0.9463667);
        verify(mockRequestOnline, times(1)).getRateResponse(anyString(), anyString());
    }

    @Test
    public void testRateOffline() {
        CurrencyRequestOffline mockRequestOffline = mock(CurrencyRequestOffline.class);
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
                return "{\"meta\":{\"code\":200,\"disclaimer\":\"Usage subject to terms: https:\\/\\/currencyscoop.com\\/terms\"},\"response\":{\"date\":\"2022-04-29T09:24:51Z\",\"base\":\""+"USD"+"\",\"rates\":{\""+ "EUR" +"\":1}}}";

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
        when(mockRequestOffline.getRateResponse(anyString(), anyString())).thenReturn(response);
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOffline);

        assertEquals(api.getRate("USD", "EUR"), 1);
        verify(mockRequestOffline, times(1)).getRateResponse(anyString(), anyString());
    }

    @Test
    public void testPastebinOnline(){
        PastebinRequestOnline mockRequestOnline = mock(PastebinRequestOnline.class);
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
                return "https://pastebin.com/NR2Gp4JM";
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

        when(mockRequestOnline.getPastebinResponse(anyString())).thenReturn(response);
        PastebinAPI api = new PastebinAPI(true);
        api.setRequest(mockRequestOnline);

        assertEquals(api.createPastin("this is from test suits"), "https://pastebin.com/NR2Gp4JM");
        verify(mockRequestOnline, times(1)).getPastebinResponse(anyString());
    }

    @Test
    public void testPastebinOffline(){
        PastebinRequestOffline mockRequestOffline = mock(PastebinRequestOffline.class);
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
                return "https://pastebin.com/NR2Gp4JM";
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

        when(mockRequestOffline.getPastebinResponse(anyString())).thenReturn(response);
        PastebinAPI api = new PastebinAPI(false);
        api.setRequest(mockRequestOffline);

        assertEquals(api.createPastin("this is from test suits"), "https://pastebin.com/NR2Gp4JM");
        verify(mockRequestOffline, times(1)).getPastebinResponse(anyString());
    }
}
