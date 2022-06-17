import model.CurrencyScoopAPIOnline;
import model.Model;
import model.POJOs.Convert;
import model.POJOs.Rate;
import model.PastebinAPIOnline;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This class tests all the currency store related functionalities.
 */
public class ModelCurrencyTest {
    /**
     * Test add currency to model.
     */
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

    /**
     * Test remove currency from model.
     */
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

    /**
     * Test get country name from model.
     */
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

    /**
     * Test clear countries from model.
     */
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

    /**
     * Test calculate result.
     */
    @Test
    public void testCalculateResult(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        Rate rate = new Rate("USD", "AUD", 1.5);
        Convert result = model.calculateResult(1, rate);
        assertEquals(1.5, result.getResult(), 0.001);

    }

    /**
     * Test check threshold.
     */
    @Test
    public void testCheckAndSetThreshold(){
        CurrencyScoopAPIOnline mockInputOnline = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline mockOutputOnline = mock(PastebinAPIOnline.class);
        Model model = new Model(mockInputOnline, mockOutputOnline);
        model.setThreshold(0.5);
        assertTrue(model.checkThreshold(0.5));
        assertFalse(model.checkThreshold(0.4));

        boolean check = model.setThreshold(1.2);
        assertFalse(check);

        boolean check2 = model.setThreshold(-1);
        assertFalse(check2);

        boolean check3 = model.setThreshold(0.1);
        assertFalse(check3);

        boolean check4 = model.setThreshold(1);
        assertFalse(check4);
    }
}
