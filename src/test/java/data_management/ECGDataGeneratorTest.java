package data_management;

import com.cardio_generator.generators.ECGDataGenerator;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ECGDataGeneratorTest {
    @Test
    void testGenerateECGData() {
        ECGDataGenerator generator = new ECGDataGenerator(1);
        ConsoleOutputStrategy outputStrategy = new ConsoleOutputStrategy();
        assertDoesNotThrow(() -> generator.generate(1, outputStrategy));
    }
}