package com.alerts;

// This interface defines the structure for an alert in a healthcare system. But uses the old implementation witout the decorator pattern.

public class BasicAlert implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    /**
     * Constructs a BasicAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId The ID of the patient associated with the alert.
     * @param condition The condition that triggered the alert.
     * @param timestamp The time when the alert was generated.
     */
    public BasicAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public String getPatientId() { return patientId; }
    @Override
    public String getCondition() { return condition; }
    @Override
    public long getTimestamp() { return timestamp; }
}