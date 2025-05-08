package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Tests the DataStorage class.
 * Assumptions:
 * - If a patient does not exist, methods like getRecords should return an empty list.
 * - DataStorage can handle multiple patients and their records.
 */
class DataStorageTest {

    @Test
    void testAddAndRetrievePatientData() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add data for a patient
        storage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);

        // Retrieve records for the patient
        List<PatientRecord> records = storage.getRecords(1, 0, System.currentTimeMillis());

        // Verify the record
        assertEquals(1, records.size());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals("HeartRate", records.get(0).getRecordType());
    }

    @Test
    void testGetRecordsForNonExistentPatient() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Try to get records for a non-existent patient
        List<PatientRecord> records = storage.getRecords(999, 0, System.currentTimeMillis());

        // Verify that the result is an empty list
        assertTrue(records.isEmpty());
    }

    @Test
    void testAddAndRetrieveMultiplePatients() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add data for multiple patients
        storage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);
        storage.addPatientData(2, 95.0, "Saturation", 1714376789051L);

        // Verify data for patient 1
        List<PatientRecord> records1 = storage.getRecords(1, 0, System.currentTimeMillis());
        assertEquals(1, records1.size());
        assertEquals(120.0, records1.get(0).getMeasurementValue());

        // Verify data for patient 2
        List<PatientRecord> records2 = storage.getRecords(2, 0, System.currentTimeMillis());
        assertEquals(1, records2.size());
        assertEquals(95.0, records2.get(0).getMeasurementValue());
    }
}
