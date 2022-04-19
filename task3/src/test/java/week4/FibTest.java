package week4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibTest {
    @Test
    public void testFib() {
        assertEquals(144, Fib.fibClassic(12));
        assertEquals(144, Fib.tailRecFib(12));
    }
}