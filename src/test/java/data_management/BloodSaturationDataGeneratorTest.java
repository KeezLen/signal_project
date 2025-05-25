package data_management;

import com.cardio_generator.generators.BloodSaturationDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodSaturationDataGeneratorTest {

    @Test
    void testGenerateOutputsSaturation() {
        List<String> labels = new ArrayList<>();
        OutputStrategy mockOutput = (patientId, timestamp, label, data) -> labels.add(label);

        BloodSaturationDataGenerator generator = new BloodSaturationDataGenerator(1);
        generator.generate(1, mockOutput);

        assertTrue(labels.contains("Saturation"), "Should output Saturation label");
    }
}