package data_management;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WebSocketOutputStrategyTest {

    /**
     * Tests the WebSocketOutputStrategy constructor and output method.
     * This test checks if the constructor initializes correctly and if the output method can be called without exceptions.
     */
    @Test
    void testConstructorAndOutputNoClient() {
        // Use a random port between 30000 and 40000 to avoid conflicts
        int port = 30000 + (int)(Math.random() * 10000);
        WebSocketOutputStrategy strategy = new WebSocketOutputStrategy(port);
        // Should not throw even if no client is connected
        assertDoesNotThrow(() -> strategy.output(1, 123L, "TestLabel", "TestData"));
    }
}