package com.cardio_generator.generators;

/**
 * Strategy interface for alert checking.
 */
public interface AlertStrategy {
    boolean checkAlert(int patientId, double... values);
}