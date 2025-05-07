package com.data_management;

import java.io.*;
import java.nio.file.*;

public class FileDataReader implements DataReader {
    private String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        // Get all the files in the selected directory
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    // Read each file for every line
                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Split the line into parts
                            String[] parts = line.split(",");
                            int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                            long timestamp = Long.parseLong(parts[1].split(":")[1].trim());
                            String label = parts[2].split(":")[1].trim();
                            double value = Double.parseDouble(parts[3].split(":")[1].trim());

                            // Finally add the data to DataStorage
                            dataStorage.addPatientData(patientId, value, label, timestamp);
                        }
                    }
                }
            }
        }
    }
}