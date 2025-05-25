package com.alerts;

public class BasicAlert implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

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