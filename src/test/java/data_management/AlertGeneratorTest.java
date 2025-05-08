package data_management;

import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

/**
 * Tests the AlertGenerator class.
 * Makes sure alerts are triggered correctly.
 */
class AlertGeneratorTest {

    @Test
    void testEvaluateData() {
        // Create a DataStorage instance
        DataStorage storage = new DataStorage();

        // Add mock patient data
        storage.addPatientData(1, 190.0, "SystolicPressure", 1714376789050L); // High systolic
        storage.addPatientData(1, 91.0, "Saturation", 1714376789052L); // Low saturation

        // Get the patient from storage
        Patient patient = storage.getAllPatients().get(0);

        // Create an AlertGenerator
        AlertGenerator generator = new AlertGenerator(storage);

        // Evaluate the patient's data
        generator.evaluateData(patient);

        // Alerts are printed to the console
    }
}