package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates and updates blood pressure data for each patient around a baseline.
 * Uses a configurable AlertStrategy to check for critical blood pressure values.
 */
public class BloodPressureDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();

    private final int[] lastSystolicValues;
    private final int[] lastDiastolicValues;
    private final AlertStrategy alertStrategy;

    /**
     * Constructs a BloodPressureDataGenerator with a default
     * BloodPressureStrategy for determining alerts.
     *
     * @param patientCount The number of patients in the system
     */
    public BloodPressureDataGenerator(int patientCount) {
        this(patientCount, new BloodPressureStrategy());
    }

    /**
     * Constructs a BloodPressureDataGenerator with a custom alert strategy.
     *
     * @param patientCount  Number of patients in the system
     * @param alertStrategy Custom strategy for generating blood pressure alerts
     */
    public BloodPressureDataGenerator(int patientCount, AlertStrategy alertStrategy) {
        lastSystolicValues = new int[patientCount + 1];
        lastDiastolicValues = new int[patientCount + 1];
        this.alertStrategy = alertStrategy;

        // Initialize baseline values for each patient (1-indexed for convenience)
        for (int i = 1; i <= patientCount; i++) {
            lastSystolicValues[i] = 110 + random.nextInt(20); // ~110 to 130
            lastDiastolicValues[i] = 70 + random.nextInt(15); // ~70 to 85
        }
    }

    /**
     * Generates new blood pressure values for the specified patient and sends them out.
     * Also checks alerts via the specified AlertStrategy.
     *
     * @param patientId      Unique identifier of the patient
     * @param outputStrategy Strategy for outputting generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Small random variations in the systolic/diastolic values
            int systolicVariation = random.nextInt(5) - 2;  // -2..2
            int diastolicVariation = random.nextInt(5) - 2; // -2..2

            int newSystolicValue = lastSystolicValues[patientId] + systolicVariation;
            int newDiastolicValue = lastDiastolicValues[patientId] + diastolicVariation;

            // Clamp values to realistic blood pressure ranges
            newSystolicValue = Math.min(Math.max(newSystolicValue, 90), 180);
            newDiastolicValue = Math.min(Math.max(newDiastolicValue, 60), 120);

            lastSystolicValues[patientId] = newSystolicValue;
            lastDiastolicValues[patientId] = newDiastolicValue;

            long now = System.currentTimeMillis();
            outputStrategy.output(patientId, now, "SystolicPressure",  String.valueOf(newSystolicValue));
            outputStrategy.output(patientId, now, "DiastolicPressure", String.valueOf(newDiastolicValue));

            // Check if the new values trigger a blood pressure alert
            if (alertStrategy != null && alertStrategy.checkAlert(patientId, newSystolicValue, newDiastolicValue)) {
                outputStrategy.output(patientId, now, "BloodPressureAlert",
                        "ALERT: Critical blood pressure detected!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood pressure data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
