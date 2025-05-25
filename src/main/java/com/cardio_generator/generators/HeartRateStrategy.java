package com.cardio_generator.generators;

// checks for abnormal heart rates
public class HeartRateStrategy implements AlertStrategy {
    @Override
    public boolean checkAlert(int patientId, double... values) {
        // values[0] = heart rate
        if (values.length < 1) return false;
        double heartRate = values[0];
        return heartRate < 50 || heartRate > 120;
    }
}