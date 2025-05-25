package com.alerts.AlertFactory;

import com.alerts.*;

public class ECGAlertFactory extends com.alerts.AlertFactoryMain {

    /**
     * Creates an alert for ECG anomalies or related events.
     *
     * @param patientId  The patient identifier
     * @param condition  Description of the ECG alert condition
     * @param timestamp  When the alert was triggered
     * @return           A BasicAlert object for the given ECG condition
     */
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }
}
