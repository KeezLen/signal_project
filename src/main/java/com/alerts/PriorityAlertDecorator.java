package com.alerts;

// Thiss class is a decorator for the Alert interface that adds priority information to the alert.
public class PriorityAlertDecorator extends AlertDecorator {
    private String priority;

    /**
     * Constructs a PriorityAlertDecorator with the specified decorated alert and priority.
     * @param decoratedAlert
     * @param priority
     */
    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public String getCondition() {
        return "[PRIORITY: " + priority + "] " + decoratedAlert.getCondition();
    }

    public String getPriority() {
        return priority;
    }
}