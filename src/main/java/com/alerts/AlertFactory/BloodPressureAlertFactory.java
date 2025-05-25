package com.alerts.AlertFactory;

import com.alerts.*;

public class BloodPressureAlertFactory extends com.alerts.AlertFactoryMain {

    /**
     * Creates a BasicAlert when a patient's blood pressure triggers an alert.
     *
     * @param patientId  The patient's identifier
     * @param condition  The alert description (e.g., "High Blood Pressure")
     * @param timestamp  When the alert was triggered
     * @return           A new BasicAlert object
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }
}
