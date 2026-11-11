
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilePathTest {

    @Test
    public void testFilePath() {

        // Duong dan viet cung theo Windows
        File file = new File(
                "src\\test\\resources\\sample.txt"
        );

        // Kiem tra file co ton tai khong
        assertTrue(file.exists(),
                "File khong ton tai!");
    }
}

