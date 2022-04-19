package week3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class StorageConsumerManualTest {
    private StorageMock mock = null;
    private StorageConsumer fixture = null;

    @BeforeEach
    void setup() {
        mock = new StorageMock();
        fixture = new StorageConsumer(mock);
    }

    @Test
    void getFirstElementPopulated() {
        mock.setReturnValueForGetElements(new String[]{"first", "second"});
        assertThat(fixture.getFirstElement(), equalTo("first"));
    }

    @Test
    void getFirstElementEmpty() {
        mock.setReturnValueForGetElements(new String[]{});
        assertThat(fixture.getFirstElement(), is(nullValue()));
    }

    @Test
    void addNext() {
        fixture.addNext();
        assertThat(mock.getLastCalledAddElementValue(), equalTo("1"));
        fixture.addNext();
        assertThat(mock.getLastCalledAddElementValue(), equalTo("2"));
    }
}
