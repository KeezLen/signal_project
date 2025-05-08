package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

/**
 * Tests the DataStorage class.
 * Makes sure data is stored and retrieved correctly.
 */
class DataStorageTest {

    @Test
    void testAddAndGetRecords() throws IOException {

        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add mock data
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        // Get the records
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);

        // Check the records
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(200.0, records.get(1).getMeasurementValue());
    }
}
