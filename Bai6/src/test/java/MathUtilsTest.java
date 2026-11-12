import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    private final MathUtils math = new MathUtils();

    @Test
    void testAdd() {
        assertEquals(10, math.add(5, 5));
    }

    @Test
    void testSubtract() {
        assertEquals(5, math.subtract(10, 5));
    }

    @Test
    void testMultiply() {
        assertEquals(20, math.multiply(4, 5));
    }

    @Test
    void testDivide() {
        assertEquals(5, math.divide(10, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class,
                () -> math.divide(10, 0));
    }
}
