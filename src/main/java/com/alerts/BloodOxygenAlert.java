package com.alerts;

public class BloodOxygenAlert extends BasicAlert {

    /**
     * Constructs a BloodOxygenAlert with the specified patient ID, condition, and timestamp.
     *
     * @param patientId The ID of the patient associated with the alert.
     * @param condition The condition that triggered the alert.
     * @param timestamp The time when the alert was generated.
     */
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }
}