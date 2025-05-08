package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a patient.
 * It stores the patient's ID and their medical records.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Creates a new patient with the given ID.
     * Starts with no records.
     *
     * @param patientId The patient's unique ID.
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record for this patient.
     *
     * @param measurementValue The value of the measurement.
     * @param recordType       The type of record (e.g., "HeartRate").
     * @param timestamp        When the measurement was taken.
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Gets all records for this patient within a time range.
     *
     * @param startTime The start of the time range.
     * @param endTime   The end of the time range.
     * @return A list of records within the time range.
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> filteredRecords = new ArrayList<>();

        // Iterate through all patient records
        for (PatientRecord record : patientRecords) {
            // Check if the record's timestamp falls within the specified range
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }

    /**
     * Gets the patient's ID.
     *
     * @return The patient's ID.
     */
    public int getPatientId() {
        return patientId;
    }
}
