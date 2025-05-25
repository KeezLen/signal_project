package data_management;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;

class AlertGeneratorTest {

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