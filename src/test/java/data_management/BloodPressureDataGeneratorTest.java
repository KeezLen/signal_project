package data_management;

import com.cardio_generator.generators.BloodPressureDataGenerator;
import com.cardio_generator.generators.AlertStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodPressureDataGeneratorTest {

    @Test
    void testGenerateAlwaysTriggersAlert() {
        List<String> outputs = new ArrayList<>();
        OutputStrategy mockOutput = (patientId, timestamp, label, data) -> {
            if (label.equals("BloodPressureAlert")) outputs.add(data);
        };

        // Custom strategy that always triggers an alert
        AlertStrategy alwaysAlert = (patientId, values) -> true;

        BloodPressureDataGenerator generator = new BloodPressureDataGenerator(1, alwaysAlert);
        generator.generate(1, mockOutput);

        assertFalse(outputs.isEmpty(), "Should trigger at least one alert");
        assertTrue(outputs.get(0).contains("ALERT"), "Alert message should contain 'ALERT'");
    }

    @Test
    void testGenerateDoesNotTriggerAlert() {
        List<String> outputs = new ArrayList<>();
        OutputStrategy mockOutput = (patientId, timestamp, label, data) -> {
            if (label.equals("BloodPressureAlert")) outputs.add(data);
        };

        // Custom strategy that never triggers an alert
        AlertStrategy neverAlert = (patientId, values) -> false;

        BloodPressureDataGenerator generator = new BloodPressureDataGenerator(1, neverAlert);
        generator.generate(1, mockOutput);

        assertTrue(outputs.isEmpty(), "Should not trigger any alert");
    }
}