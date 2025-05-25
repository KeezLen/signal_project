package data_management;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ECGDataGeneratorTest {

    /**
     * Tests that the ECGDataGenerator outputs ECG data with correct labels.
     */
    @Test
    void testECGOutputLabel() {
        List<String> labels = new ArrayList<>();
        OutputStrategy mockOutput = (id, ts, label, data) -> labels.add(label);
        ECGDataGenerator gen = new ECGDataGenerator(1);
        gen.generate(1, mockOutput);
        assertTrue(labels.stream().anyMatch(l -> l.contains("ECG")), "Should output ECG label");
    }
}