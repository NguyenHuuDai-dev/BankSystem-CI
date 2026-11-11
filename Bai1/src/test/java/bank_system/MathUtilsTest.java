package bank_system;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    @Test
    public void testAdd() {

        int result = MathUtils.add(2, 3);

        assertEquals(5, result);
    }
}
