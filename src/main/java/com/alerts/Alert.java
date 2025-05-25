package com.alerts;

// This interface defines the structure for an alert in a healthcare system.
public interface Alert {
    String getPatientId();
    String getCondition();
    long getTimestamp();
}