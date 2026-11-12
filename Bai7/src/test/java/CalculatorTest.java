import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addReturnsSum() {
        assertEquals(8, calculator.add(3, 5));
    }

    @Test
    void subtractReturnsDifference() {
        assertEquals(2, calculator.subtract(5, 3));
    }
}