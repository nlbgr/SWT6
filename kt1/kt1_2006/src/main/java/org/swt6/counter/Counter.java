package org.swt6.counter;

import java.util.ArrayList;
import java.util.List;

public class Counter {
    private final int threshold;
    private int count = 0;
    private List<CounterListener> listeners = new ArrayList<>();

    public Counter(int threshold) {
        this.threshold = threshold;
    }

    public void increment() {
        count++;

        if (count > threshold) {
            listeners.forEach(l -> l.thresholdExceeded(new ThresholdExceededEvent(count)));
        }
    }

    public void addListener(CounterListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CounterListener listener) {
        listeners.remove(listener);
    }
}
