package week2;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {

    @Test
    public void doSomethingTest() {
        // given
        MyDependency mock = mock(MyDependency.class);
        MyClass fixture = new MyClass(mock);

        // when
        when(mock.getSomeValue()).thenReturn(900);
        int value = fixture.doSomething();
        // then

        assertEquals(900,value);
        verify(mock).getSomeValue();
    }

}