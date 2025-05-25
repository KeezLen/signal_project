package com.cardio_generator.generators;

/**
 * Checks for low oxygen saturation.
 */
public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(int patientId, double... values) {
        // values[0] = oxygen saturation
        if (values.length < 1) return false;
        double saturation = values[0];
        return saturation < 92;
    }
}