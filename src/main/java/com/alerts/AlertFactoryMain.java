package com.alerts;

public abstract class AlertFactoryMain {

    /**
     * Creates an Alert for a given patient, condition, and time of occurrence.
     *
     * @param patientId the patient's identifier
     * @param condition the alert condition or description
     * @param timestamp the time the alert occurred
     * @return an Alert representing the described condition
     */
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}
