package data_management;

import com.cardio_generator.outputs.ConsoleOutputStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleOutputStrategyTest {

    /**
     * Tests that the ConsoleOutputStrategy does not throw an exception when outputting data.
     * This verifies that the console output functionality is working as expected.
     */
    @Test
    void testOutputDoesNotThrow() {
        ConsoleOutputStrategy strategy = new ConsoleOutputStrategy();
        assertDoesNotThrow(() -> strategy.output(1, System.currentTimeMillis(), "TestLabel", "TestData"));
    }
}