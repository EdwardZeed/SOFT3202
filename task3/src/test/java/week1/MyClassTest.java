package week1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {

    @Test
    void getValue() {
        assertEquals(4, MyClass.getValue(), "getValue returned the wrong value");
    }
}