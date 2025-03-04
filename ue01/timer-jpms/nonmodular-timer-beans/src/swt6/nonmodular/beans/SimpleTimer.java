package swt6.nonmodular.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleTimer {
    private int tickInterval = 1000;
    private int numberTicks = 1;
    private int tickCount = 0;
    private AtomicBoolean stopTimer = new AtomicBoolean(false); // Atomic typen verwenden Compare-And-Swap / Compare-And-Set um blocking free zu arbeiten -> sehr schnell
    private AtomicReference<Thread> tickerThread = new AtomicReference<>(null);
    private final List<TimerListener> listeners = new CopyOnWriteArrayList<>();

    public SimpleTimer(final int tickInterval, final int numberTicks) {
        this.tickInterval = tickInterval;
        this.numberTicks = numberTicks;
    }

    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Timer is already running");
        }

        tickerThread.set(new Thread(() -> {
            int numTicks = this.numberTicks;
            int interval = this.tickInterval;

            while (!stopTimer.get() && tickCount < numTicks) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) { }

                if (!stopTimer.get()) {
                    tickCount++;
                    fireEvent(new TimerEvent(SimpleTimer.this, numTicks,tickCount));
                }
            }

            tickerThread.set(null);
            stopTimer.set(false);
        }));

        tickerThread.get().start();
    }

    private void fireEvent(final TimerEvent timerEvent) {
        listeners.forEach((l) -> l.expired(timerEvent));
    }

    public void stop() {
        stopTimer.set(true);
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(final int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public int getNumberTicks() {
        return numberTicks;
    }

    public void setNumberTicks(final int numberTicks) {
        this.numberTicks = numberTicks;
    }

    public boolean isRunning() {
        return tickerThread.get() != null;
    }

    public void addTimerListener(final TimerListener listener) {
        listeners.add(listener);
    }

    public void removeTimerListener(final TimerListener listener) {
        listeners.remove(listener);
    }
}
