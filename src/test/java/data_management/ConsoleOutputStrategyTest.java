package data_management;

import com.cardio_generator.outputs.ConsoleOutputStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleOutputStrategyTest {
    @Test
    void testOutputDoesNotThrow() {
        ConsoleOutputStrategy strategy = new ConsoleOutputStrategy();
        assertDoesNotThrow(() -> strategy.output(1, System.currentTimeMillis(), "TestLabel", "TestData"));
    }
}