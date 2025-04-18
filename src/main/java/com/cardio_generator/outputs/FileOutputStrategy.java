package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Outputs patient data to files in a specified directory.
 * This class implements the OutputStrategy interface and writes data to files based on the label provided. Each label corresponds to a separate file.
 *
 */
public class FileOutputStrategy implements OutputStrategy {

    // Changed variable name to camelCase
    /**
     * The base directory where output files will be created.
     */
    private String baseDirectory;

    // Changed variable name to camelCase and made it private for encapsulation
    /**
     * A map to store file paths for each label to avoid recomputing paths.
     */
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    // Changed method to a proper constructor
    /**
     * Constructs a FileOutputStrategy with the specified base directory.
     *
     * @param baseDirectory The directory where output files will be created.
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs patient data to a file corresponding to the given label.
     * If the file does not exist, it will be created. Data is appended to the file.
     *
     * @param patientId The ID of the patient whose data is being output.
     * @param timestamp The time at which the data was generated, in milliseconds since the UNIX epoch.
     * @param label     A label describing the type of data (e.g., "ECG", "Alert").
     * @param data      The actual data to be written to the file.
     * @throws IOException If an error occurs while creating directories or writing to the file.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
                        Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Changed variable name to camelCase
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            // Split long line for better readability
            out.printf(
                "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                patientId, timestamp, label, data
            );
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}