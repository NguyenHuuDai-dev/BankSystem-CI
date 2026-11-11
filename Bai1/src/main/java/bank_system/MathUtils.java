package bank_system;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathUtils {

    private static final Logger logger =
            LoggerFactory.getLogger(MathUtils.class);

    public static int add(int a, int b) {

        logger.info("Starting addition: a={}, b={}", a, b);

        int result = a + b;

        logger.info("Addition completed: result={}", result);

        return result;
    }
}
