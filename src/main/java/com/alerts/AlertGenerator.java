package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * This class checks patient data and creates alerts if something is wrong.
 * It uses DataStorage to get patient data and checks for specific conditions.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructor for AlertGenerator.
     * It needs DataStorage to get patient data.
     *
     * @param dataStorage The storage where patient data is kept.
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Checks a patient's data to see if any alerts need to be triggered.
     * Calls different methods to check for specific problems.
     *
     * @param patient The patient whose data will be checked.
     */
    public void evaluateData(Patient patient) {
        // Get all the patient's records
        List<PatientRecord> records = patient.getRecords(0, System.currentTimeMillis());

        // Check for different types of alerts
        checkBloodPressureAlerts(records, patient.getPatientId());
        checkBloodSaturationAlerts(records, patient.getPatientId());
        checkCombinedAlert(records, patient.getPatientId());
        checkHeartRateAlerts(records, patient.getPatientId()); // Check heart rate too
    }

    /**
     * Checks if the patient's blood pressure is too high, too low, or trending badly.
     *
     * @param records   The patient's records to check.
     * @param patientId The ID of the patient.
     */
    private void checkBloodPressureAlerts(List<PatientRecord> records, int patientId) {
        int consecutiveTrendCount = 0; // Counts how many times the trend continues
        double lastSystolic = 0, lastDiastolic = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure")) {
                double currentSystolic = record.getMeasurementValue();

                // Check if systolic pressure is too high or too low
                if (currentSystolic > 180 || currentSystolic < 90) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Critical Systolic Pressure", record.getTimestamp()));
                }

                // Check for trends (e.g., increasing or decreasing)
                if (Math.abs(currentSystolic - lastSystolic) > 10) {
                    consecutiveTrendCount++;
                } else {
                    consecutiveTrendCount = 0; // Reset if the trend stops
                }

                // Trigger an alert if the trend continues for 3 readings
                if (consecutiveTrendCount >= 3) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Blood Pressure Trend Alert", record.getTimestamp()));
                    consecutiveTrendCount = 0; // Reset after triggering
                }

                lastSystolic = currentSystolic; // Update the last systolic value
            } else if (record.getRecordType().equals("DiastolicPressure")) {
                double currentDiastolic = record.getMeasurementValue();

                // Check if diastolic pressure is too high or too low
                if (currentDiastolic > 120 || currentDiastolic < 60) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Critical Diastolic Pressure", record.getTimestamp()));
                }

                lastDiastolic = currentDiastolic; // Update the last diastolic value
            }
        }
    }

    /**
     * Checks if the patient's blood oxygen saturation is too low.
     *
     * @param records   The patient's records to check.
     * @param patientId The ID of the patient.
     */
    private void checkBloodSaturationAlerts(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("Saturation")) {
                double saturation = record.getMeasurementValue();

                // Trigger an alert if saturation is below 92%
                if (saturation < 92) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Low Saturation Alert", record.getTimestamp()));
                }
            }
        }
    }

    /**
     * Checks if the patient has both low blood pressure and low oxygen saturation.
     *
     * @param records   The patient's records to check.
     * @param patientId The ID of the patient.
     */
    private void checkCombinedAlert(List<PatientRecord> records, int patientId) {
        boolean lowSystolic = false;
        boolean lowSaturation = false;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure") && record.getMeasurementValue() < 90) {
                lowSystolic = true;
            }
            if (record.getRecordType().equals("Saturation") && record.getMeasurementValue() < 92) {
                lowSaturation = true;
            }
        }

        // Trigger an alert if both conditions are true
        if (lowSystolic && lowSaturation) {
            triggerAlert(new Alert(String.valueOf(patientId), "Hypotensive Hypoxemia Alert", System.currentTimeMillis()));
        }
    }

    /**
     * Checks if the patient's heart rate is too high or too low.
     *
     * @param records   The patient's records to check.
     * @param patientId The ID of the patient.
     */
    private void checkHeartRateAlerts(List<PatientRecord> records, int patientId) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("HeartRate")) {
                double heartRate = record.getMeasurementValue();

                // Trigger an alert if heart rate is abnormal
                if (heartRate > 120 || heartRate < 50) {
                    triggerAlert(new Alert(String.valueOf(patientId), "Abnormal Heart Rate Alert", record.getTimestamp()));
                }
            }
        }
    }

    /**
     * Creates an alert and prints it to the console.
     *
     * @param alert The alert to be triggered.
     */
    private void triggerAlert(Alert alert) {
        // Print the alert details
        System.out.println("ALERT: " + alert.getCondition() + " for Patient ID: " + alert.getPatientId() + " at " + alert.getTimestamp());
    }
}
