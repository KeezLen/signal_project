package com.alerts.AlertFactory;

import com.alerts.Alert;
import com.alerts.ECGAlert;

public class ECGAlertFactory extends com.alerts.AlertFactoryMain {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, condition, timestamp);
    }
}
