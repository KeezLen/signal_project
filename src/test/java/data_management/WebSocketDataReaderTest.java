package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import org.junit.jupiter.api.Test;

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
}
