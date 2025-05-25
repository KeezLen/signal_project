package com.alerts;

public abstract class AlertFactoryMain {
    public abstract Alert createAlert(String patientId, String condition, long timestamp);
}
