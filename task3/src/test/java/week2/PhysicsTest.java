package week2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest {

    @Test
    void checkCollision() {
        Physics fixture = new Physics();

        assertFalse(fixture.checkCollision(
                10.0,
                10.0,
                0.0,
                0.0,
                2.0));
    }
}