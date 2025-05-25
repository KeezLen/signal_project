package com.cardio_generator.generators;

/**
 * Checks for critical blood pressure values.
 */
public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(int patientId, double... values) {
        // values[0] = systolic, values[1] = diastolic
        if (values.length < 2) return false;
        double systolic = values[0];
        double diastolic = values[1];
        return systolic >= 160 || diastolic >= 100;
    }
}