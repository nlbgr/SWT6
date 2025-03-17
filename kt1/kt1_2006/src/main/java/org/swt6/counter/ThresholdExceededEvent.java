package org.swt6.counter;

public class ThresholdExceededEvent {
    int counter;

    public ThresholdExceededEvent(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }
}
