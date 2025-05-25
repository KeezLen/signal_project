package data_management;

import com.cardio_generator.outputs.FileOutputStrategy;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class FileOutputStrategyTest {

    /**
     * Tests that the FileOutputStrategy creates a file and writes data correctly.
     * Verifies that the output file exists and contains the expected content.
     */
    @Test
    void testOutputCreatesFileAndWritesData() throws IOException {
        Path tempDir = Files.createTempDirectory("outputTest");
        FileOutputStrategy strategy = new FileOutputStrategy(tempDir.toString());

        strategy.output(1, 123L, "TestLabel", "TestData");

        Path outputFile = tempDir.resolve("TestLabel.txt");
        assertTrue(Files.exists(outputFile), "Output file should be created");
        String content = Files.readString(outputFile);
        assertTrue(content.contains("Patient ID: 1"), "Output should contain patient ID");
        assertTrue(content.contains("TestData"), "Output should contain data");

        // Clean up
        Files.deleteIfExists(outputFile);
        Files.deleteIfExists(tempDir);
    }
}