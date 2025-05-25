package com.alerts.AlertFactory;

import com.alerts.*;

public class BloodOxygenAlertFactory extends com.alerts.AlertFactoryMain {

    /**
     * Creates a BasicAlert for the specified patient and condition.
     *
     * @param patientId   ID of the patient who triggered the alert
     * @param condition   Description of the alert condition (e.g. "Low Blood Oxygen")
     * @param timestamp   Time the alert was triggered
     * @return            A new BasicAlert object
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }
}
