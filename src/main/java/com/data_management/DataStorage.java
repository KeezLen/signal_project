package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {
    private static DataStorage instance; // Singleton instance

    private Map<Integer, Patient> patientMap; // Stores patient objects by their ID.

    // Private constructor prevents instantiation from other classes
    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    // Static method to get the singleton instance
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Adds data for a patient. If the patient doesn't exist, a new one is created.
     *
     * @param patientId The patient's ID.
     * @param measurementValue The value of the measurement.
     * @param recordType The type of record (e.g., "HeartRate").
     * @param timestamp The time the measurement was taken.
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    /**
     * Gets records for a patient within a time range.
     *
     * @param patientId The patient's ID.
     * @param startTime The start of the time range.
     * @param endTime The end of the time range.
     * @return A list of records within the time range.
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // Return an empty list if no patient is found.
    }

    /**
     * Gets all patients stored in the system.
     *
     * @return A list of all patients.
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    // clears all patient data from the storage
    public void clear() {
        patientMap.clear();
    }

    /**
     * The main method for testing DataStorage.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            DataStorage storage = DataStorage.getInstance();

            // Add mock data
            storage.addPatientData(1, 120.0, "HeartRate", 1714376789050L);
            storage.addPatientData(1, 80.0, "BloodPressure", 1714376789055L);
            storage.addPatientData(2, 95.0, "Saturation", 1714376789060L);

            // Print records for a patient
            List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
            for (PatientRecord record : records) {
                System.out.println("Record for Patient ID: " + record.getPatientId() +
                        ", Type: " + record.getRecordType() +
                        ", Data: " + record.getMeasurementValue() +
                        ", Timestamp: " + record.getTimestamp());
            }

            // Initialize AlertGenerator and evaluate data
            AlertGenerator alertGenerator = new AlertGenerator(storage);
            for (Patient patient : storage.getAllPatients()) {
                alertGenerator.evaluateData(patient);
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
