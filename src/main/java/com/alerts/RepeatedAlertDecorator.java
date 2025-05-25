package com.alerts;

// This class is a decorator for the Alert interface that adds repeat information to the alert.
public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;

    /**
     * Constructs a RepeatedAlertDecorator with the specified decorated alert and repeat count.
     *
     * @param decoratedAlert The alert to be decorated.
     * @param repeatCount The number of times the alert has been repeated.
     */
    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition() + " (Repeated " + repeatCount + " times)";
    }

    public int getRepeatCount() {
        return repeatCount;
    }
}