package org.swt6.counter;

@FunctionalInterface
public interface CounterListener {
    void thresholdExceeded(ThresholdExceededEvent event);
}
