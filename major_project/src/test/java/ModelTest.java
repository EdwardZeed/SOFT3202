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

public class ModelTest {
    @Test
    public void testConvertOnline() {
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

        assertThrows(IllegalArgumentException.class, () -> api.convert("US", "EUR", 1));
        assertThrows(IllegalArgumentException.class, () -> api.convert("USD", "E", 1));
        assertThrows(IllegalArgumentException.class, () -> api.convert("US", "EU", 1));

    }


    @Test
    public void testConvertOffline(){
        CurrencyRequestOffline mockRequestOffline = mock(CurrencyRequestOffline.class);

        Convert convert = new Convert(0.94720926, "USD", "EUR");
        when(mockRequestOffline.getConvert(anyString(), anyString(), anyDouble())).thenReturn(convert);

        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOffline);

        Convert result;
        try {
            result = api.convert("USD", "EUR", 1);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result.getResult(), 0.94720926);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        verify(mockRequestOffline, times(1)).getConvert(anyString(), anyString(), anyDouble());

    }

    @Test
    public void testRateOnline() {
        CurrencyRequestOnline mockRequestOnline = mock(CurrencyRequestOnline.class);
        Database mockDatabase = mock(Database.class);

        Rate rate = new Rate("USD","EUR", 0.9463667);
        try {
            when(mockRequestOnline.getRate(anyString(), anyString())).thenReturn(rate);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        CurrencyScoopAPI api = new CurrencyScoopAPI(true);
        api.setRequest(mockRequestOnline);
        api.setDb(mockDatabase);

        Rate result;
        try {
            result = api.getRate("USD", "EUR", true);
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
        Database mockDatabase = mock(Database.class);
        api.setDb(mockDatabase);

        assertThrows(IllegalArgumentException.class, () -> api.getRate("US", "EU", true));
        assertThrows(IllegalArgumentException.class, () -> api.getRate("US", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> api.getRate("USD", "EU", true));
        assertThrows(IllegalArgumentException.class, () -> api.getRate("USD", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> api.getRate("US", "EUR", false));
        assertThrows(IllegalArgumentException.class, () -> api.getRate("US", "EUR", true));

    }

    @Test
    public void testRateOffline() {
        CurrencyRequestOffline mockRequestOffline = mock(CurrencyRequestOffline.class);
        Database mockDatabase = mock(Database.class);

        Rate rate = new Rate("USD","EUR", 1);
        when(mockRequestOffline.getRate(anyString(), anyString())).thenReturn(rate);
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.setRequest(mockRequestOffline);
        api.setDb(mockDatabase);

        Rate result1;
        Rate result2;
        try {
            result1 = api.getRate("USD", "EUR", true);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result1.getRate(), 1);
        assertEquals(result1.getFrom(), "USD");
        assertEquals(result1.getTo(), "EUR");
        verify(mockRequestOffline, times(1)).getRate(anyString(), anyString());

        try {
            result2 = api.getRate("USD", "EUR", false);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result2.getRate(), 1);
        assertEquals(result2.getFrom(), "USD");
        assertEquals(result2.getTo(), "EUR");
        verify(mockRequestOffline, times(2)).getRate(anyString(), anyString());
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

    @Test
    public void testAddCurrency(){
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.addCountry("United States", "USD");
        api.addCountry("Australia", "AUD");

        assertEquals(2, api.getCountries().size());
        assertEquals("USD", api.getCountries().get("United States"));
        assertEquals("AUD", api.getCountries().get("Australia"));

        api.addCountry("United States", "USD");
        assertEquals(2, api.getCountries().size());
        assertEquals("USD", api.getCountries().get("United States"));
        assertEquals("AUD", api.getCountries().get("Australia"));

    }

    @Test
    public void testRemoveCurrency(){
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.addCountry("United States", "USD");
        api.addCountry("Australia", "AUD");
        assertEquals(2, api.getCountries().size());

        api.remove("Australia");

        assertEquals(1, api.getCountries().size());
        assertEquals("USD", api.getCountries().get("United States"));
        assertEquals(null, api.getCountries().get("Australia"));
    }

    @Test
    public void testGetCountries(){
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.addCountry("United States", "USD");
        api.addCountry("Australia", "AUD");
        assertEquals("United States", api.getFromCountry("USD"));
        assertEquals("Australia", api.getToCountry("AUD"));
    }

    @Test
    public void testClearCountries(){
        CurrencyScoopAPI api = new CurrencyScoopAPI(false);
        api.addCountry("United States", "USD");
        api.addCountry("Australia", "AUD");
        assertEquals(2, api.getCountries().size());
        api.clear();
        assertEquals(0, api.getCountries().size());
    }

}
