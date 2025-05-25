package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import com.cardio_generator.outputs.WebSocketOutputStrategy;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketDataReaderTest {
    @Test
    void testMalformedMessageHandling() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        WebSocketDataReader reader = new WebSocketDataReader();

        // Simulate malformed messages
        String[] malformedMessages = {
            "", // empty
            "1,not_a_timestamp,HeartRate,120.0", // invalid timestamp
            "1,1714376789050,HeartRate", // missing value
            "abc,1714376789050,HeartRate,120.0", // invalid patientId
            "1,1714376789050,HeartRate,not_a_number", // invalid value
            "1,1714376789050", // too short
            null // null message
        };

        for (String msg : malformedMessages) {
            // Use reflection to access the protected onMessage method for testing
            try {
                var method = WebSocketDataReader.class.getDeclaredMethod("handleMessage", String.class, DataStorage.class);
                method.setAccessible(true);
                method.invoke(reader, msg, storage);
            } catch (Exception e) {
                // Should not throw, errors should be handled internally
                fail("Exception thrown for malformed message: " + msg + " - " + e.getMessage());
            }
        }

        // No records should be added for malformed messages
        assertTrue(storage.getAllPatients().isEmpty(), "No patient data should be added for malformed messages");
    }

    @Test
    void testWebSocketConnectionFailure() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        // Use a port where no server is running
        int unusedPort = 54321;
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:" + unusedPort);

        // Capture System.err output
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        assertDoesNotThrow(() -> reader.start(storage), "Should not throw even if server is unavailable");

        // Wait a bit for connection attempt
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        System.setErr(originalErr);

        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Connection refused") || errorOutput.contains("Failed to connect"),
                "Error output should indicate connection failure. Actual: " + errorOutput);
    }

    @Test
    void testServerDisconnectsUnexpectedly() throws Exception {
        int port = 24680;
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        // Start server and client
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(port);
        WebSocketDataReader client = new WebSocketDataReader("ws://localhost:" + port);
        client.start(storage);

        // Wait for connection
        Thread.sleep(500);

        // Capture System.err output
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        // Stop the server to simulate disconnect
        server.stopServer();

        // Wait for client to detect disconnect
        Thread.sleep(500);

        System.setErr(originalErr);

        String errorOutput = errContent.toString();
        assertTrue(
            errorOutput.contains("WebSocket connection closed") || errorOutput.contains("WebSocket error"),
            "Error output should indicate server disconnect. Actual: " + errorOutput
        );
    }

    @Test
    void testDataLossOnServerDisconnect() throws Exception {
        int port = 24681;
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        // Start server and client
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(port);
        WebSocketDataReader client = new WebSocketDataReader("ws://localhost:" + port);
        client.start(storage);

        // Wait for connection
        Thread.sleep(500);

        // Send messages before disconnect
        server.output(1, System.currentTimeMillis(), "HeartRate", "70.0");
        server.output(2, System.currentTimeMillis(), "ECG", "0.95");

        // Wait for messages to be processed
        Thread.sleep(300);

        // Stop the server to simulate disconnect
        server.stopServer();

        // Try to send more messages after disconnect (these should not be received)
        try {
            server.output(1, System.currentTimeMillis(), "HeartRate", "80.0");
            server.output(2, System.currentTimeMillis(), "ECG", "1.05");
        } catch (Exception ignored) {}

        // Wait for any late messages
        Thread.sleep(300);

        // Check that only the first messages are present
        boolean patient1Has70 = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == 1)
            .flatMap(p -> p.getRecords().stream())
            .anyMatch(r -> r.getRecordType().equals("HeartRate") && r.getMeasurementValue() == 70.0);

        boolean patient2Has095 = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == 2)
            .flatMap(p -> p.getRecords().stream())
            .anyMatch(r -> r.getRecordType().equals("ECG") && r.getMeasurementValue() == 0.95);

        boolean patient1Has80 = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == 1)
            .flatMap(p -> p.getRecords().stream())
            .anyMatch(r -> r.getRecordType().equals("HeartRate") && r.getMeasurementValue() == 80.0);

        boolean patient2Has105 = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == 2)
            .flatMap(p -> p.getRecords().stream())
            .anyMatch(r -> r.getRecordType().equals("ECG") && r.getMeasurementValue() == 1.05);

        assertTrue(patient1Has70, "Patient 1 should have HeartRate 70.0 before disconnect");
        assertTrue(patient2Has095, "Patient 2 should have ECG 0.95 before disconnect");
        assertFalse(patient1Has80, "Patient 1 should NOT have HeartRate 80.0 after disconnect");
        assertFalse(patient2Has105, "Patient 2 should NOT have ECG 1.05 after disconnect");
    }
}
