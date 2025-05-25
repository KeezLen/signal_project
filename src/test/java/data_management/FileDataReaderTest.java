package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class FileDataReaderTest {

    @Test
    void testReadData() throws IOException {
        String fakeDirectoryPath = "fake_directory";
        FileDataReader reader = new FileDataReader(fakeDirectoryPath);

        DataStorage storage = DataStorage.getInstance();

        storage.clear();

        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        assertEquals(2, storage.getRecords(1, 1714376789050L, 1714376789051L).size());
        assertEquals(100.0, storage.getRecords(1, 1714376789050L, 1714376789051L).get(0).getMeasurementValue());
        assertEquals(200.0, storage.getRecords(1, 1714376789050L, 1714376789051L).get(1).getMeasurementValue());
    }

    @Test
    void testReadDataReadsPatientDataFromFile() throws IOException {
        // Create a temporary directory and file
        Path tempDir = Files.createTempDirectory("fileDataReaderTest");
        Path dataFile = tempDir.resolve("patient_data.txt");

        // Write sample data in the expected format
        String line1 = "PatientID: 1, Timestamp: 1714376789050, Label: HeartRate, Value: 120.0";
        String line2 = "PatientID: 2, Timestamp: 1714376789051, Label: Saturation, Value: 98.0";
        Files.write(dataFile, List.of(line1, line2));

        // Prepare DataStorage and FileDataReader
        DataStorage storage = DataStorage.getInstance();
        storage.clear();
        FileDataReader reader = new FileDataReader(tempDir.toString());

        // Act
        reader.readData(storage);

        // Assert
        assertEquals(1, storage.getRecords(1, 0, Long.MAX_VALUE).size());
        assertEquals(120.0, storage.getRecords(1, 0, Long.MAX_VALUE).get(0).getMeasurementValue());
        assertEquals("HeartRate", storage.getRecords(1, 0, Long.MAX_VALUE).get(0).getRecordType());

        assertEquals(1, storage.getRecords(2, 0, Long.MAX_VALUE).size());
        assertEquals(98.0, storage.getRecords(2, 0, Long.MAX_VALUE).get(0).getMeasurementValue());
        assertEquals("Saturation", storage.getRecords(2, 0, Long.MAX_VALUE).get(0).getRecordType());

        // Clean up
        Files.deleteIfExists(dataFile);
        Files.deleteIfExists(tempDir);
    }
}