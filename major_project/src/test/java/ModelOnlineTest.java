import model.*;
import model.request.*;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ModelOnlineTest {

    @Test
    public void testConvertOnline()  {
        CurrencyRequestOnline mockRequestOnline = mock(CurrencyRequestOnline.class);

        Convert convert = new Convert(0.94720926, "USD", "EUR");
        try {
            when(mockRequestOnline.getConvert(anyString(), anyString(), anyDouble())).thenReturn(convert);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        api.setRequest(mockRequestOnline);

        Convert result;
        try {
            result = api.convert("USD", "EUR", 1);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result.getResult(), 0.94720926);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        try {
            verify(mockRequestOnline, times(1)).getConvert(anyString(), anyString(), anyDouble());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConvertOnlineInvalid()  {
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        Convert result1;
        try {
            result1 = api.convert("US", "EUR", 1);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(result1.getResult(), 0.0);
        assertEquals(result1.getFrom(), "US");
        assertEquals(result1.getTo(), "EUR");

        Convert result2;
        try {
            result2 = api.convert("USD", "E", 1);
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(result2.getResult(), 0.0);
        assertEquals(result2.getFrom(), "USD");
        assertEquals(result2.getTo(), "E");

        Convert result3;
        try {
            result3 = api.convert("US", "EU", 1);
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(result3.getResult(), 0.0);
        assertEquals(result3.getFrom(), "US");
        assertEquals(result3.getTo(), "EU");

    }


    @Test
    public void testConvertOffline() throws URISyntaxException, IOException, InterruptedException {
        CurrencyRequestOffline mockRequestOnline = mock(CurrencyRequestOffline.class);

        Convert convert = new Convert(0.94720926, "USD", "EUR");
        when(mockRequestOnline.getConvert(anyString(), anyString(), anyDouble())).thenReturn(convert);

        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOnline);

        Convert result = api.convert("USD", "EUR", 1);

        assertEquals(result.getResult(), 0.94720926);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        verify(mockRequestOnline, times(1)).getConvert(anyString(), anyString(), anyDouble());
    }

    @Test
    public void testRateOnline() {
        CurrencyRequestOnline mockRequestOnline = mock(CurrencyRequestOnline.class);

        Rate rate = new Rate("USD","EUR", 0.9463667);
        try {
            when(mockRequestOnline.getRate(anyString(), anyString())).thenReturn(rate);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        api.setRequest(mockRequestOnline);

        Rate result;
        try {
            result = api.getRate("USD", "EUR");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result.getRate(), 0.9463667);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        try {
            verify(mockRequestOnline, times(1)).getRate(anyString(), anyString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetRateInvalid(){
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        Rate result;
        try {
            result = api.getRate("US", "EUR");
        }
        catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(result.getRate(), 0.0);
        assertEquals(result.getFrom(), "US");
        assertEquals(result.getTo(), "EUR");

        assertThrows(IllegalArgumentException.class, () -> api.getRate("US", "EU"));

        assertThrows(IllegalArgumentException.class, () -> api.getRate("USD", "EU"));
    }

    @Test
    public void testRateOffline() {
        CurrencyRequestOffline mockRequestOffline = mock(CurrencyRequestOffline.class);

        Rate rate = new Rate("USD","EUR", 1);
        when(mockRequestOffline.getRate(anyString(), anyString())).thenReturn(rate);
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOffline);

        Rate result;
        try {
            result = api.getRate("USD", "EUR");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result.getRate(), 1);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        verify(mockRequestOffline, times(1)).getRate(anyString(), anyString());
    }

    @Test
    public void testPastebinOnline(){
        PastebinRequestOnline mockRequestOnline = mock(PastebinRequestOnline.class);
        PastebinResult pastebinResult = new PastebinResult("https://pastebin.com/NR2Gp4JM");
        when(mockRequestOnline.getPastebinResponse(anyString())).thenReturn(pastebinResult);
        PastebinAPI api = new PastebinAPI(true);
        api.setRequest(mockRequestOnline);

        assertEquals(api.createPastin("this is from test suits").getURI(), "https://pastebin.com/NR2Gp4JM");
        verify(mockRequestOnline, times(1)).getPastebinResponse(anyString());
    }

    @Test
    public void testPastebinOffline(){
        PastebinRequestOffline mockRequestOffline = mock(PastebinRequestOffline.class);
        PastebinResult result = new PastebinResult("https://pastebin.com/NR2Gp4JM");

        when(mockRequestOffline.getPastebinResponse(anyString())).thenReturn(result);
        PastebinAPI api = new PastebinAPI(false);
        api.setRequest(mockRequestOffline);

        assertEquals(api.createPastin("this is from test suits").getURI(), "https://pastebin.com/NR2Gp4JM");
        verify(mockRequestOffline, times(1)).getPastebinResponse(anyString());
    }
}
