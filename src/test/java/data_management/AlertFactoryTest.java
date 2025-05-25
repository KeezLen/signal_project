package data_management;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;
import com.alerts.BloodOxygenAlert;
import com.alerts.ECGAlert;
import com.alerts.AlertFactoryMain;
import com.alerts.AlertFactory.BloodPressureAlertFactory;
import com.alerts.AlertFactory.BloodOxygenAlertFactory;
import com.alerts.AlertFactory.ECGAlertFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlertFactoryTest {
    @Test
    void testBloodPressureAlertFactory() {
        AlertFactoryMain factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "High Systolic", 123L);
        assertTrue(alert instanceof BloodPressureAlert);
        assertEquals("1", alert.getPatientId());
        assertEquals("High Systolic", alert.getCondition());
        assertEquals(123L, alert.getTimestamp());
    }

    @Test
    void testBloodOxygenAlertFactory() {
        AlertFactoryMain factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("2", "Low Oxygen", 456L);
        assertTrue(alert instanceof BloodOxygenAlert);
    }

    @Test
    void testECGAlertFactory() {
        AlertFactoryMain factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("3", "Irregular ECG", 789L);
        assertTrue(alert instanceof ECGAlert);
    }
}
