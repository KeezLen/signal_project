package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class represents a generator for patient data in a healthcare monitoring simulation.
 * The interface defines the contract for generating patient-specific data.
 *
 */
public interface PatientDataGenerator {

    /**
     * Generates data for a specific patient and outputs it with the given strategy.
     *
     * @param patientId      The ID of the patient for whom data is being generated.
     * @param outputStrategy The strategy used to output the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
