package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Tests the Patient class.
 * Makes sure records are added and retrieved correctly.
 */
class PatientTest {

    @Test
    void testGetRecords() {
        // Create a patient
        Patient patient = new Patient(1);

        // Add records
        patient.addRecord(100.0, "HeartRate", 1714376789050L);
        patient.addRecord(120.0, "HeartRate", 1714376789055L);
        patient.addRecord(80.0, "HeartRate", 1714376789060L);

        // Get records within a time range
        List<PatientRecord> records = patient.getRecords(1714376789050L, 1714376789055L);

        // Check the records
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(120.0, records.get(1).getMeasurementValue());
    }
}