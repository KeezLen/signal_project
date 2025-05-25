package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates blood-related data (cholesterol, WBC, RBC) for each patient around a baseline.
 * Each call to generate simulates current values and outputs them in real time.
 */
public class BloodLevelsDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] baselineCholesterol;
    private final double[] baselineWhiteCells;
    private final double[] baselineRedCells;

    /**
     * Initializes baseline values for each patient.
     * @param patientCount total number of patients in the system
     */
    public BloodLevelsDataGenerator(int patientCount) {
        // Arrays start at index 1 for direct patientId referencing
        baselineCholesterol = new double[patientCount + 1];
        baselineWhiteCells  = new double[patientCount + 1];
        baselineRedCells    = new double[patientCount + 1];

        // Assign random starting baselines for each patient
        for (int i = 1; i <= patientCount; i++) {
            baselineCholesterol[i] = 150 + random.nextDouble() * 50;
            baselineWhiteCells[i]  = 4   + random.nextDouble() * 6;
            baselineRedCells[i]    = 4.5 + random.nextDouble() * 1.5;
        }
    }

    /**
     * Generates updated blood levels for the specified patient and outputs the data.
     * @param patientId unique identifier for the patient
     * @param outputStrategy strategy for sending out the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Add minor variations around each patient's baseline
            double cholesterol = baselineCholesterol[patientId] + (random.nextDouble() - 0.5) * 10;
            double whiteCells  = baselineWhiteCells[patientId]  + (random.nextDouble() - 0.5) * 1;
            double redCells    = baselineRedCells[patientId]    + (random.nextDouble() - 0.5) * 0.2;

            // Output the generated data with the current timestamp
            long now = System.currentTimeMillis();
            outputStrategy.output(patientId, now, "Cholesterol",    String.valueOf(cholesterol));
            outputStrategy.output(patientId, now, "WhiteBloodCells", String.valueOf(whiteCells));
            outputStrategy.output(patientId, now, "RedBloodCells",   String.valueOf(redCells));
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood levels data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
