package com.alerts.AlertFactory;

import com.alerts.*;

public class BloodOxygenAlertFactory extends com.alerts.AlertFactoryMain {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }
}
