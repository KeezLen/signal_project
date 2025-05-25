package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

class PatientTest {

    /**
     * Tests the functionality of adding and retrieving patient records.
     */
    @Test
    void testAddAndRetrieveRecords() {
        // Create a patient
        Patient patient = new Patient(1);

        // Add records
        patient.addRecord(100.0, "HeartRate", 1714376789050L);
        patient.addRecord(120.0, "HeartRate", 1714376789055L);

        // Retrieve records within a time range
        List<PatientRecord> records = patient.getRecords(1714376789050L, 1714376789055L);

        // Verify the records
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(120.0, records.get(1).getMeasurementValue());
    }

    /**
     * Tests the functionality of retrieving records for a patient with no records.
     */
    @Test
    void testGetRecordsWithNoData() {
        // Create a patient
        Patient patient = new Patient(1);

        // Try to get records when no data has been added
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

        // Verify that the result is an empty list
        assertTrue(records.isEmpty());
    }

    /**
     * Tests the functionality of retrieving records outside a specified time range.
     */
    @Test
    void testGetRecordsOutsideTimeRange() {
        // Create a patient
        Patient patient = new Patient(1);

        // Add records
        patient.addRecord(100.0, "HeartRate", 1714376789050L);

        // Try to get records outside the time range
        List<PatientRecord> records = patient.getRecords(1714376789000L, 1714376789049L);

        // Verify that the result is an empty list
        assertTrue(records.isEmpty());
    }
}