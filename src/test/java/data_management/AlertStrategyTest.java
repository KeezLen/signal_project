package data_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cardio_generator.generators.AlertStrategy;
import com.cardio_generator.generators.BloodPressureStrategy;
import com.cardio_generator.generators.HeartRateStrategy;
import com.cardio_generator.generators.OxygenSaturationStrategy;

public class AlertStrategyTest {

    @Test
    void testBloodPressureStrategy() {
        AlertStrategy strategy = new BloodPressureStrategy();
        assertFalse(strategy.checkAlert(1, 120, 80));
        assertTrue(strategy.checkAlert(1, 170, 80));
        assertTrue(strategy.checkAlert(1, 120, 110));
        assertTrue(strategy.checkAlert(1, 170, 110));
    }

    @Test
    void testHeartRateStrategy() {
        AlertStrategy strategy = new HeartRateStrategy();
        assertFalse(strategy.checkAlert(1, 70));
        assertTrue(strategy.checkAlert(1, 40));
        assertTrue(strategy.checkAlert(1, 130));
        assertFalse(strategy.checkAlert(1)); // No value
    }

    @Test
    void testOxygenSaturationStrategy() {
        AlertStrategy strategy = new OxygenSaturationStrategy();
        assertFalse(strategy.checkAlert(1, 98));
        assertTrue(strategy.checkAlert(1, 90));
    }
}