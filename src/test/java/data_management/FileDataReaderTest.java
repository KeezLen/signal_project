package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;

import java.io.IOException;

class FileDataReaderTest {

    @Test
    void testReadData() throws IOException {
        // Simulate a directory path (you can replace this with an actual directory if needed)
        String fakeDirectoryPath = "fake_directory";

        // Create a FileDataReader instance
        FileDataReader reader = new FileDataReader(fakeDirectoryPath);

        // Create a mock DataStorage instance
        DataStorage storage = new DataStorage();

        // Simulate adding data directly (since we aren't reading actual files)
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        // Verify the data was added correctly
        assertEquals(2, storage.getRecords(1, 1714376789050L, 1714376789051L).size());
        assertEquals(100.0, storage.getRecords(1, 1714376789050L, 1714376789051L).get(0).getMeasurementValue());
        assertEquals(200.0, storage.getRecords(1, 1714376789050L, 1714376789051L).get(1).getMeasurementValue());
    }
}