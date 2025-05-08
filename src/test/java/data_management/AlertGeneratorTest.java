package data_management;

import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

/**
 * Tests the AlertGenerator class.
 * Assumptions:
 * - Alerts are triggered based on predefined thresholds for blood pressure, saturation, and heart rate.
 * - Alerts are printed to the console, so manual verification is required unless modified to store alerts.
 */
class AlertGeneratorTest {

    @Test
    void testEvaluateDataTriggersAlerts() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add mock patient data
        storage.addPatientData(1, 190.0, "SystolicPressure", 1714376789050L); // High systolic
        storage.addPatientData(1, 85.0, "Saturation", 1714376789051L); // Low saturation
        storage.addPatientData(1, 45.0, "HeartRate", 1714376789052L); // Low heart rate

        // Get the patient from storage
        Patient patient = storage.getAllPatients().get(0);

        // Create an AlertGenerator
        AlertGenerator generator = new AlertGenerator(storage);

        // Evaluate the patient's data
        generator.evaluateData(patient);

        // Assumption: Alerts are printed to the console. Manual verification is required.
        // Alternatively, modify AlertGenerator to store alerts in a list for automated verification.
    }

    @Test
    void testEvaluateDataWithNoAlerts() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add normal patient data
        storage.addPatientData(1, 120.0, "SystolicPressure", 1714376789050L); // Normal systolic
        storage.addPatientData(1, 98.0, "Saturation", 1714376789051L); // Normal saturation
        storage.addPatientData(1, 75.0, "HeartRate", 1714376789052L); // Normal heart rate

        // Get the patient from storage
        Patient patient = storage.getAllPatients().get(0);

        // Create an AlertGenerator
        AlertGenerator generator = new AlertGenerator(storage);

        // Evaluate the patient's data
        generator.evaluateData(patient);

        // Assumption: No alerts should be triggered. Manual verification is required.
    }

    @Test
    void testEvaluateDataWithEdgeCases() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add edge case data
        storage.addPatientData(1, 180.0, "SystolicPressure", 1714376789050L); // Upper limit for systolic
        storage.addPatientData(1, 92.0, "Saturation", 1714376789051L); // Lower limit for saturation
        storage.addPatientData(1, 50.0, "HeartRate", 1714376789052L); // Lower limit for heart rate

        // Get the patient from storage
        Patient patient = storage.getAllPatients().get(0);

        // Create an AlertGenerator
        AlertGenerator generator = new AlertGenerator(storage);

        // Evaluate the patient's data
        generator.evaluateData(patient);

        // Assumption: No alerts should be triggered as the values are at the limits but not critical.
    }

    @Test
    void testEvaluateDataWithMultiplePatients() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add data for multiple patients
        storage.addPatientData(1, 190.0, "SystolicPressure", 1714376789050L); // High systolic for patient 1
        storage.addPatientData(2, 85.0, "Saturation", 1714376789051L); // Low saturation for patient 2

        // Get the patients from storage
        Patient patient1 = storage.getAllPatients().get(0);
        Patient patient2 = storage.getAllPatients().get(1);

        // Create an AlertGenerator
        AlertGenerator generator = new AlertGenerator(storage);

        // Evaluate data for both patients
        generator.evaluateData(patient1);
        generator.evaluateData(patient2);

        // Assumption: Alerts are printed to the console for both patients. Manual verification is required.
    }
}