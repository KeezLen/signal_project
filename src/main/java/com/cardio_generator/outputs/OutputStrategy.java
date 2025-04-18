package com.cardio_generator.outputs;

/**
 * Defines the contract for outputting patient data in a healthcare monitoring simulation.
 * Implementations of this interface specify how the data is output.
 *
 */
public interface OutputStrategy {

    /**
     * Outputs patient data using the specified parameters.
     *
     * @param patientId The ID of the patient whose data is being output.
     * @param timestamp The time at which the data was generated, in milliseconds since the UNIX epoch.
     * @param label     A label describing the type of data (e.g., "Alert", "ECG").
     * @param data      The actual data to be output (e.g., measurement values or alert status).
     */
    void output(int patientId, long timestamp, String label, String data);
}
