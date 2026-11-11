import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    MathUtils math = new MathUtils();

    @Test
    public void testAdd() {
        assertEquals(10, math.add(5, 5));
    }

    @Test
    public void testSubtract() {
        assertEquals(5, math.subtract(10, 5));
    }

    @Test
    public void testMultiply() {
        assertEquals(20, math.multiply(4, 5));
    }

    @Test
    public void testDivide() {
        assertEquals(5, math.divide(10, 2));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> math.divide(10, 0)
        );
    }
}
