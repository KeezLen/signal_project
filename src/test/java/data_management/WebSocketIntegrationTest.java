package data_management;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import com.alerts.AlertGenerator;
import com.data_management.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketIntegrationTest {

    /**
     * This test verifies the integration of WebSocket data flow and alert generation.
     * It checks if the WebSocket server can send data, the client can receive it,
     * and the AlertGenerator can evaluate the data to generate alerts.
     */
    @AfterEach
    void cleanup() {
        DataStorage.getInstance().clear();
    }

    /**
     * Tests the WebSocket data flow and alert generation.
     * It starts a WebSocket server, connects a client, sends data that should trigger an alert,
     * and verifies that the alert is generated correctly.
     */
    @Test
    void testWebSocketDataFlowAndAlertGeneration() throws Exception {
        int port = 24567; // Use a random high port to avoid conflicts

        // Start WebSocket server (output)
        WebSocketOutputStrategy server = new WebSocketOutputStrategy(port);

        // Start WebSocket client (input)
        DataStorage storage = DataStorage.getInstance();
        WebSocketDataReader client = new WebSocketDataReader("ws://localhost:" + port);
        client.start(storage);

        // Wait for the client to connect
        Thread.sleep(500);

        // Send data that should trigger an alert (e.g., low saturation)
        int patientId = 1;
        long timestamp = System.currentTimeMillis();
        String label = "Saturation";
        String value = "85.0"; // Below alert threshold

        server.output(patientId, timestamp, label, value);

        // Wait for the message to be processed
        Thread.sleep(500);

        // Check that the data arrived in storage
        Patient patient = storage.getAllPatients().stream()
            .filter(p -> p.getPatientId() == patientId)
            .findFirst()
            .orElse(null);

        assertNotNull(patient, "Patient should exist in storage");
        assertTrue(patient.getRecords(0, System.currentTimeMillis()).stream()
            .anyMatch(r -> r.getRecordType().equals(label) && r.getMeasurementValue() == Double.parseDouble(value)),
            "Correct record should exist for patient");

        // Capture alert output 
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run alert generation
        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);

        // Restore original System.out
        System.setOut(originalOut);

        // Check that an alert message was printed
        String output = outContent.toString();
        assertTrue(output.contains("ALERT") && output.contains("Low Saturation Alert"),
                "Alert output should contain 'ALERT' and 'Low Saturation Alert'. Actual output: " + output);
    }
}
