package data_management;

import com.cardio_generator.outputs.TcpOutputStrategy;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class TcpOutputStrategyTest {

    /**
     * Tests that the TcpOutputStrategy can be constructed and does not throw an exception
     * when no client is connected.
     * This verifies that the server can start without any clients connected.
     */
    @Test
    void testConstructorAndOutputNoClient() {
        // Use a random port between 20000 and 30000 to avoid conflicts
        int port = 20000 + (int)(Math.random() * 10000);
        TcpOutputStrategy strategy = new TcpOutputStrategy(port);
        // Should not throw even if no client is connected
        assertDoesNotThrow(() -> strategy.output(1, 123L, "TestLabel", "TestData"));
    }

    /**
     * Tests that the TcpOutputStrategy can output data when a client is connected.
     * This verifies that the server can send data to a connected client.
     */
    @Test
    void testOutputWithClientConnected() throws Exception {
        int port = 21000 + (int)(Math.random() * 10000);
        TcpOutputStrategy strategy = new TcpOutputStrategy(port);

        // Connect a client socket to the server
        try (Socket client = new Socket("localhost", port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            // Wait a bit for the server to accept the client and set up the output stream
            Thread.sleep(200);

            // Send output
            strategy.output(42, 999L, "TestLabel", "TestData");

            // Read the line sent by the server
            String line = reader.readLine();
            assertNotNull(line, "Should receive a line from the server");
            assertTrue(line.contains("42"), "Output should contain patient ID");
            assertTrue(line.contains("TestLabel"), "Output should contain label");
            assertTrue(line.contains("TestData"), "Output should contain data");
        }
    }
}