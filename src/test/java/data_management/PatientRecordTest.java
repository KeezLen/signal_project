package data_management;

import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the PatientRecord class to ensure it correctly initializes and retrieves field values.
 */
class PatientRecordTest {
    @Test
    void testPatientRecordFields() {
        PatientRecord record = new PatientRecord(1, 99.9, "TestType", 12345L);
        assertEquals(1, record.getPatientId());
        assertEquals(99.9, record.getMeasurementValue());
        assertEquals("TestType", record.getRecordType());
        assertEquals(12345L, record.getTimestamp());
    }
}
