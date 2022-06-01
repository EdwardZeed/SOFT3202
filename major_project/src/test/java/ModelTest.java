import model.*;
import model.POJOs.Convert;
import model.POJOs.PastebinResult;
import model.POJOs.Rate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ModelTest {
    @Test
    public void testConvertOnline() {
        CurrencyScoopAPIOnline mockAPIOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockAPIOutputOnline = mock(PastebinAPIOnline.class);

        Convert convert = new Convert(0.94720926, "USD", "EUR");
        try {
            when(mockAPIOnline.convert(anyString(), anyString(), anyDouble())).thenReturn(convert);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Model model = new Model(mockAPIOnline, mockAPIOutputOnline);
        model.setInputAPI(mockAPIOnline);

        Convert result;
        result = model.getConvert("USD", "EUR", 1);

        assertEquals(result.getResult(), 0.94720926);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        try {
            verify(mockAPIOnline, times(1)).convert(anyString(), anyString(), anyDouble());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConvertOnlineInvalid()  {
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        CurrencyScoopAPIOffline mockInputOffline = mock(CurrencyScoopAPIOffline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        PastebinAPIOffline mockOutputOffline = mock(PastebinAPIOffline.class);
        Model model1 = new Model(mockInputOnline, mockOutputOnline);

        assertThrows(IllegalArgumentException.class, () -> model1.getConvert("US", "EUR", 1));
        assertThrows(IllegalArgumentException.class, () -> model1.getConvert("USD", "E", 1));
        assertThrows(IllegalArgumentException.class, () -> model1.getConvert("US", "EU", 1));

        Model model2 = new Model(mockInputOffline, mockOutputOffline);
        assertThrows(IllegalArgumentException.class, () -> model2.getConvert("US", "EUR", 1));
        assertThrows(IllegalArgumentException.class, () -> model2.getConvert("USD", "E", 1));
        assertThrows(IllegalArgumentException.class, () -> model2.getConvert("US", "EU", 1));

        Model model3 = new Model(mockInputOnline, mockOutputOffline);
        assertThrows(IllegalArgumentException.class, () -> model3.getConvert("US", "EUR", 1));
        assertThrows(IllegalArgumentException.class, () -> model3.getConvert("USD", "E", 1));
        assertThrows(IllegalArgumentException.class, () -> model3.getConvert("US", "EU", 1));

        Model model4 = new Model(mockInputOffline, mockOutputOnline);
        assertThrows(IllegalArgumentException.class, () -> model4.getConvert("US", "EUR", 1));
        assertThrows(IllegalArgumentException.class, () -> model4.getConvert("USD", "E", 1));
        assertThrows(IllegalArgumentException.class, () -> model4.getConvert("US", "EU", 1));

    }


    @Test
    public void testConvertOffline(){
        CurrencyScoopAPIOffline mockInputOffline = mock(CurrencyScoopAPIOffline.class);
        PastebinAPIOffline mockOutputOffline = mock(PastebinAPIOffline.class);

        Convert convert = new Convert(0.94720926, "USD", "EUR");
        when(mockInputOffline.convert(anyString(), anyString(), anyDouble())).thenReturn(convert);

        Model model = new Model(mockInputOffline, mockOutputOffline);
        model.setInputAPI(mockInputOffline);

        Convert result;
        result = model.getConvert("USD", "EUR", 1);

        assertEquals(result.getResult(), 0.94720926);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        verify(mockInputOffline, times(1)).convert(anyString(), anyString(), anyDouble());

    }

    @Test
    public void testRateOnline() {
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);

        Rate rate = new Rate("USD","EUR", 0.9463667);
        try {
            when(mockInputOnline.getRate(anyString(), anyString())).thenReturn(rate);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.setInputAPI(mockInputOnline);


        Rate result;
        result = model.getRate("USD", "EUR", false);

        assertEquals(result.getRate(), 0.9463667);
        assertEquals(result.getFrom(), "USD");
        assertEquals(result.getTo(), "EUR");
        try {
            verify(mockInputOnline, times(1)).getRate(anyString(), anyString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetRateInvalid(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        CurrencyScoopAPIOffline mockInputOffline = mock(CurrencyScoopAPIOffline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        PastebinAPIOffline mockOutputOffline = mock(PastebinAPIOffline.class);

        Model model1 = new Model(mockInputOnline, mockOutputOnline);
        assertThrows(IllegalArgumentException.class, () -> model1.getRate("US", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model1.getRate("USD", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model1.getRate("US", "EUR", false));

        Model model2 = new Model(mockInputOffline, mockOutputOffline);
        assertThrows(IllegalArgumentException.class, () -> model2.getRate("US", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model2.getRate("USD", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model2.getRate("US", "EUR", false));

        Model model3 = new Model(mockInputOnline, mockOutputOffline);
        assertThrows(IllegalArgumentException.class, () -> model3.getRate("US", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model3.getRate("USD", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model3.getRate("US", "EUR", false));

        Model model4 = new Model(mockInputOffline, mockOutputOnline);
        assertThrows(IllegalArgumentException.class, () -> model4.getRate("US", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model4.getRate("USD", "EU", false));
        assertThrows(IllegalArgumentException.class, () -> model4.getRate("US", "EUR", false));

    }

    @Test
    public void testRateOffline() {
        CurrencyScoopAPIOffline inputOffline = new CurrencyScoopAPIOffline();
        PastebinAPIOffline mockOutputOffline = mock(PastebinAPIOffline.class);

        Model model = new Model(inputOffline, mockOutputOffline);

        Rate result1;
        result1 = model.getRate("USD", "EUR", false);

        assertEquals(result1.getRate(), 0.94618129);
        assertEquals(result1.getFrom(), "USD");
        assertEquals(result1.getTo(), "EUR");

    }

    @Test
    public void testPastebinOnline(){
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);

        PastebinResult pastebinResult = new PastebinResult("https://pastebin.com/NR2Gp4JM");
        when(mockOutputOnline.createPastin(anyString())).thenReturn(pastebinResult);
        Model model = new Model(mockInputOnline, mockOutputOnline);

        assertEquals(model.postToPastebin("this is from test suits").getURI(), "https://pastebin.com/NR2Gp4JM");
        verify(mockOutputOnline, times(1)).createPastin(anyString());
    }

    @Test
    public void testPastebinOffline(){
        PastebinAPIOffline outputOffline = new PastebinAPIOffline();
        CurrencyScoopAPIOffline mockInputOffline = mock(CurrencyScoopAPIOffline.class);
        Model model = new Model(mockInputOffline, outputOffline);

        assertEquals(model.postToPastebin("this is from test suits").getURI(), "https://pastebin.com/0z5GEYtM");
    }

    @Test
    public void testAddCurrency(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.addCountry("United States", "USD");
        model.addCountry("Australia", "AUD");

        assertEquals(2, model.getCountries().size());
        assertEquals("USD", model.getCountries().get("United States"));
        assertEquals("AUD", model.getCountries().get("Australia"));

        model.addCountry("United States", "USD");
        assertEquals(2, model.getCountries().size());
        assertEquals("USD", model.getCountries().get("United States"));
        assertEquals("AUD", model.getCountries().get("Australia"));

    }

    @Test
    public void testRemoveCurrency(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.addCountry("United States", "USD");
        model.addCountry("Australia", "AUD");
        assertEquals(2, model.getCountries().size());

        model.remove("Australia");

        assertEquals(1, model.getCountries().size());
        assertEquals("USD", model.getCountries().get("United States"));
        assertNull(model.getCountries().get("Australia"));
    }

    @Test
    public void testGetCountryName(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.addCountry("United States", "USD");
        model.addCountry("Australia", "AUD");
        assertEquals("United States", model.getCountryName("USD"));
        assertEquals("Australia", model.getCountryName("AUD"));
    }

    @Test
    public void testClearCountries(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.addCountry("United States", "USD");
        model.addCountry("Australia", "AUD");
        assertEquals(2, model.getCountries().size());
        model.clear();
        assertEquals(0, model.getCountries().size());
    }

    @Test
    public void testCalculateResult(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        Rate rate = new Rate("USD", "AUD", 1.5);
        Convert result = model.calculateResult(1, rate);
        assertEquals(1.5, result.getResult(), 0.001);

    }

}
