import model.CurrencyScoopAPIOnline;
import model.Database;
import model.Model;
import model.POJOs.Rate;
import model.PastebinAPIOnline;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * This class tests all the cache related functionalities except adding and clearing cache.
 */
public class ModelCacheTest {
    /**
     * Test cache hit.
     */
    @Test
    public void testCacheHit() {
        Database db = mock(Database.class);
        when(db.getRate(anyString(), anyString())).thenReturn(1.0);
        CurrencyScoopAPIOnline inputAPI = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline outputAPI = mock(PastebinAPIOnline.class);
        when(inputAPI.isOnline()).thenReturn(true);
        Model model = new Model(inputAPI, outputAPI);
        model.setDatabase(db);

        assertTrue(model.cacheHit("USD", "EUR"));
        verify(db, times(1)).getRate(anyString(), anyString());
        verify(inputAPI, times(1)).isOnline();

    }

    /**
     * Test get rate use cache.
     */
    @Test
    public void testGetRateUseCache(){
        Database db = mock(Database.class);
        when(db.getRate(anyString(), anyString())).thenReturn(1.0);
        CurrencyScoopAPIOnline inputAPI = mock(CurrencyScoopAPIOnline.class);
        PastebinAPIOnline outputAPI = mock(PastebinAPIOnline.class);
        when(inputAPI.isOnline()).thenReturn(true);
        Model model = new Model(inputAPI, outputAPI);
        model.setDatabase(db);

        Rate rate = model.getRate("USD", "EUR", true);

        assertEquals(1.0, rate.getRate());
        assertEquals("USD", rate.getFrom());
        assertEquals("EUR", rate.getTo());
        verify(db, times(1)).getRate(anyString(), anyString());


    }
}
