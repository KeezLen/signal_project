package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates ECG (electrocardiogram) data for each patient.
 * Simulates a simplified ECG waveform with variability and outputs it in real time.
 */
public class ECGDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private double[] lastEcgValues;
    private static final double PI = Math.PI;

    /**
     * Initializes the ECG generator for the specified number of patients.
     * @param patientCount Total number of patients in the system.
     */
    public ECGDataGenerator(int patientCount) {
        lastEcgValues = new double[patientCount + 1];
        // Initialize the last ECG value for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastEcgValues[i] = 0; // Initial ECG value can be set to 0
        }
    }

    /**
     * Generates an ECG value for the specified patient and outputs it.
     * @param patientId      Unique identifier for the patient.
     * @param outputStrategy Strategy for sending out the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Generate the ECG waveform value
            double ecgValue = simulateEcgWaveform(patientId, lastEcgValues[patientId]);
            // Output the generated value
            outputStrategy.output(patientId, System.currentTimeMillis(), "ECG", Double.toString(ecgValue));
            // Update the last ECG value for the patient
            lastEcgValues[patientId] = ecgValue;
        } catch (Exception e) {
            System.err.println("An error occurred while generating ECG data for patient " + patientId);
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Simulates an ECG waveform using sinusoidal components.
     * @param patientId     The patient's ID (not used in this simplified version).
     * @param lastEcgValue  The last ECG value for the patient.
     * @return              The simulated ECG value.
     */
    private double simulateEcgWaveform(int patientId, double lastEcgValue) {
        // Simulate heart rate variability between 60 and 80 bpm
        double hr = 60.0 + random.nextDouble() * 20.0;
        double t = System.currentTimeMillis() / 1000.0; // Continuous time in seconds
        double ecgFrequency = hr / 60.0; // Convert heart rate to Hz

        // Simulate different components of the ECG signal
        double pWave = 0.1 * Math.sin(2 * PI * ecgFrequency * t); // P wave
        double qrsComplex = 0.5 * Math.sin(2 * PI * 3 * ecgFrequency * t); // QRS complex
        double tWave = 0.2 * Math.sin(2 * PI * 2 * ecgFrequency * t + PI / 4); // T wave with phase offset

        // Combine components and add small random noise
        return pWave + qrsComplex + tWave + random.nextDouble() * 0.05;
    }
}
