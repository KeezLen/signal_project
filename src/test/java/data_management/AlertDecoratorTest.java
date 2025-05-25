package data_management;

import com.alerts.Alert;
import com.alerts.BasicAlert;
import com.alerts.PriorityAlertDecorator;
import com.alerts.RepeatedAlertDecorator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlertDecoratorTest {

    @Test
    void testPriorityDecorator() {
        Alert alert = new BasicAlert("1", "Condition", 123L);
        alert = new PriorityAlertDecorator(alert, "HIGH");
        assertTrue(alert.getCondition().contains("PRIORITY: HIGH"));
    }

    @Test
    void testRepeatedDecorator() {
        Alert alert = new BasicAlert("1", "Condition", 123L);
        alert = new RepeatedAlertDecorator(alert, 2);
        assertTrue(alert.getCondition().contains("Repeated 2 times"));
    }
}