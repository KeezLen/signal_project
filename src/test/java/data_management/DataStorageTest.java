package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class DataStorageTest {

    /**
     * Tests the functionality of the DataStorage class.
     * Verifies that patient data can be added and retrieved correctly.
     */
    @Test
    void testAddAndRetrievePatientData() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        storage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);

        List<PatientRecord> records = storage.getRecords(1, 0, System.currentTimeMillis());

        assertEquals(1, records.size());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals("HeartRate", records.get(0).getRecordType());
    }

    /**
     * Tests that the DataStorage can handle multiple records for the same patient.
     */
    @Test
    void testGetRecordsForNonExistentPatient() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        List<PatientRecord> records = storage.getRecords(999, 0, System.currentTimeMillis());

        assertTrue(records.isEmpty());
    }

    /**
     * Tests that the DataStorage can handle multiple records for different patients.
     * Verifies that records are stored and retrieved correctly for each patient.
     */
    @Test
    void testAddAndRetrieveMultiplePatients() {
        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        storage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);
        storage.addPatientData(2, 95.0, "Saturation", 1714376789051L);

        List<PatientRecord> records1 = storage.getRecords(1, 0, System.currentTimeMillis());
        assertEquals(1, records1.size());
        assertEquals(120.0, records1.get(0).getMeasurementValue());

        List<PatientRecord> records2 = storage.getRecords(2, 0, System.currentTimeMillis());
        assertEquals(1, records2.size());
        assertEquals(95.0, records2.get(0).getMeasurementValue());
    }
}
