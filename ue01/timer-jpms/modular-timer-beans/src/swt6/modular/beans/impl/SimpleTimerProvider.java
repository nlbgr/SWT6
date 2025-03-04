package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerProvider;

public class SimpleTimerProvider implements TimerProvider {

    @Override
    public Timer createTimer(int interval, int numberTicks) {
        return new SimpleTimer(interval, numberTicks);
    }

    @Override
    public double timerResolution() {
        return (double) 1/1000;
    }
}
