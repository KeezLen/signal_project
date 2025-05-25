package data_management;

import com.cardio_generator.generators.BloodLevelsDataGenerator;
import com.cardio_generator.outputs.OutputStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodLevelsDataGeneratorTest {

    @Test
    void testGenerateOutputsBloodLevels() {
        List<String> labels = new ArrayList<>();
        OutputStrategy mockOutput = (patientId, timestamp, label, data) -> labels.add(label);

        BloodLevelsDataGenerator generator = new BloodLevelsDataGenerator(1);
        generator.generate(1, mockOutput);

        assertTrue(labels.stream().anyMatch(l -> l.contains("WhiteBloodCells") || l.contains("RedBloodCells")), 
            "Should output blood cell labels");
    }
}
