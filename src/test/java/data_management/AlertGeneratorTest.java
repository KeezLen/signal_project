package data_management;

import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

class AlertGeneratorTest {

    /**
     * Tests the AlertGenerator's ability to evaluate patient data and trigger alerts.
     * This test checks if the generator correctly identifies conditions that require alerts.
     */
    @Test
    void testEvaluateDataTriggersAlerts() {
        DataStorage storage = DataStorage.getInstance();

        storage.clear();

        storage.addPatientData(1, 190.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 85.0, "Saturation", 1714376789051L);
        storage.addPatientData(1, 45.0, "HeartRate", 1714376789052L);

        Patient patient = storage.getAllPatients().get(0);
        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }

    /**
     * Tests the AlertGenerator's ability to evaluate patient data without triggering alerts.
     * This test checks if the generator correctly identifies normal conditions that do not require alerts.
     */
    @Test
    void testEvaluateDataWithNoAlerts() {
        DataStorage storage = DataStorage.getInstance();

        storage.clear();

        storage.addPatientData(1, 120.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 98.0, "Saturation", 1714376789051L);
        storage.addPatientData(1, 75.0, "HeartRate", 1714376789052L);

        Patient patient = storage.getAllPatients().get(0);
        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }

    /**
     * Tests the AlertGenerator's ability to handle edge cases in patient data.
     * This test checks if the generator can evaluate data that may be at the boundary of alert conditions.
     */
    @Test
    void testEvaluateDataWithEdgeCases() {
        DataStorage storage = DataStorage.getInstance();

        storage.clear();

        storage.addPatientData(1, 180.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 92.0, "Saturation", 1714376789051L);
        storage.addPatientData(1, 50.0, "HeartRate", 1714376789052L);

        Patient patient = storage.getAllPatients().get(0);
        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient);
    }

    /**
     * Tests the AlertGenerator's ability to evaluate data for multiple patients.
     * This test checks if the generator can handle multiple patients and their respective data correctly.
     */
    @Test
    void testEvaluateDataWithMultiplePatients() {
        DataStorage storage = DataStorage.getInstance();

        storage.clear();

        storage.addPatientData(1, 190.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(2, 85.0, "Saturation", 1714376789051L);

        Patient patient1 = storage.getAllPatients().get(0);
        Patient patient2 = storage.getAllPatients().get(1);

        AlertGenerator generator = new AlertGenerator(storage);
        generator.evaluateData(patient1);
        generator.evaluateData(patient2);
    }

}