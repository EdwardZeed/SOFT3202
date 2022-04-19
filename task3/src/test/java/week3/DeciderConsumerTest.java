package week3;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

class DeciderConsumerTest {

    @Test
    public void testConsume() {
        MyCoolClass mock;
        DeciderConsumer fixture;


        // option 1, terrible idea, don't do this
        mock = mock(MyCoolClass.class);
        ArgumentCaptor<Boolean> valueCapture = ArgumentCaptor.forClass(Boolean.class);
        doNothing().when(mock).bigDecider(valueCapture.capture());
        final Boolean[] setDec =  new Boolean[1];

        when(mock.iSayWhatTheBigDeciderSaid()).thenAnswer(
                (Answer<Boolean>) invocationOnMock -> setDec[0]
        );

        // Needing to manipulate lambda/closure rules to gain access to the value by reference
        // is the code smell that indicates this is a bad idea

        fixture = new DeciderConsumer(mock, true);
        setDec[0] = valueCapture.getValue();
        assertThat(valueCapture.getValue(), equalTo(true));
        assertThat(fixture.consume(), equalTo(true));



        // option 2, much better, though should be broken up into separate methods

        // part 1 - constructor

        mock = mock(MyCoolClass.class);
        fixture = new DeciderConsumer(mock, true);
        verify(mock).bigDecider(true);

        mock = mock(MyCoolClass.class);
        fixture = new DeciderConsumer(mock, false);
        verify(mock).bigDecider(false);

        // part 2 - consume

        // void return value, so we can just ignore it
        // when(mock.bigDecider(anyBoolean()).dontCare

        // WE control the mock, WE control what will be returned at any given point, not the behaviour of
        // something we aren't testing
        mock = mock(MyCoolClass.class);
        fixture = new DeciderConsumer(mock, true); // the startDec parameter here doesn't actually matter
        when(mock.iSayWhatTheBigDeciderSaid()).thenReturn(true);
        assertThat(fixture.consume(), equalTo(true));

        mock = mock(MyCoolClass.class);
        fixture = new DeciderConsumer(mock, true); // because we control what it's going to get from the dependency
        when(mock.iSayWhatTheBigDeciderSaid()).thenReturn(false);
        assertThat(fixture.consume(), equalTo(false));
    }
}