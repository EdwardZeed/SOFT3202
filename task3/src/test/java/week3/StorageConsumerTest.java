package week3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

class StorageConsumerTest {
    private Storage mock = null;
    private StorageConsumer fixture = null;

    @BeforeEach
    void setup() {
        mock = mock(Storage.class);
        fixture = new StorageConsumer(mock);
    }

    @Test
    void getFirstElementPopulated() {
        when(mock.getElements()).thenReturn(new String[]{"first", "second"});
        assertThat(fixture.getFirstElement(), equalTo("first"));
    }

    @Test
    void getFirstElementEmpty() {
        when(mock.getElements()).thenReturn(new String[]{});
        assertThat(fixture.getFirstElement(), is(nullValue()));
    }

    @Test
    void addNext() {
        fixture.addNext();
        verify(mock).addElement("1");
        fixture.addNext();
        verify(mock).addElement("2");
    }
}