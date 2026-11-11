import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilePathTest {

    @Test
    public void testFilePath() {

        Path path = Path.of(
                "src",
                "test",
                "resources",
                "sample.txt"
        );

        assertTrue(Files.exists(path),
                "File khong ton tai!");
    }
}



