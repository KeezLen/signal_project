package data_management;

import com.alerts.Alert;
import com.alerts.BasicAlert;
import com.alerts.AlertFactoryMain;
import com.alerts.AlertFactory.BloodPressureAlertFactory;
import com.alerts.AlertFactory.BloodOxygenAlertFactory;
import com.alerts.AlertFactory.ECGAlertFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlertFactoryTest {

    /**
     * Tests the AlertFactory classes for creating different types of alerts.
     */
    @Test
    void testBloodPressureAlertFactory() {
        AlertFactoryMain factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "High Systolic", 123L);
        assertTrue(alert instanceof BasicAlert);
        assertEquals("1", alert.getPatientId());
        assertEquals("High Systolic", alert.getCondition());
        assertEquals(123L, alert.getTimestamp());
    }

    /**
     * Tests the BloodOxygenAlertFactory for creating blood oxygen alerts.
     */
    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactoryMain factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("2", "Low Oxygen", 456L);
        assertTrue(alert instanceof BasicAlert);
        assertEquals("2", alert.getPatientId());
        assertEquals("Low Oxygen", alert.getCondition());
        assertEquals(456L, alert.getTimestamp());
    }

    /**
     * Tests the ECGAlertFactory for creating ECG alerts.
     */
    @Test
    void testECGAlertFactory() {
        AlertFactoryMain factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("3", "Irregular ECG", 789L);
        assertTrue(alert instanceof BasicAlert);
        assertEquals("3", alert.getPatientId());
        assertEquals("Irregular ECG", alert.getCondition());
        assertEquals(789L, alert.getTimestamp());
    }
}
