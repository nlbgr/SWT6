package swt6.modular.client;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.beans.TimerProvider;

import java.util.Optional;
import java.util.ServiceLoader;

public class TimerClient {

    // java --module-path ".\modular-timer-beans\;.\modular-timer-client\" --module modular.timer.client/swt6.modular.client.TimerClient
    public static void main(String[] args) {
        System.out.println("======= Test Beans ======");
        testBeans();
        System.out.println("======= Test Provider ======");
        testProvider();
    }

    private static void testBeans() {
        final Timer timer = TimerFactory.createTimer(10, 100);

        timer.addTimerListener(evt -> {
            System.out.printf("Timer expired: %d/%d%n", evt.getTickCount(), evt.getNumberTicks());
        });

        timer.start();
        sleep(500);
        timer.stop();
        System.out.println("Timer stopped");
        sleep(500);
        timer.start();

        while (timer.isRunning()) {
            sleep(10);
        }
    }

    private static void sleep(final int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ignored) { }
    }

    private static void testProvider() {
        bestTimer(10, 100).ifPresent(timer -> {
            timer.addTimerListener(evt -> {
                System.out.printf("Timer expired: %d/%d%n", evt.getTickCount(), evt.getNumberTicks());
            });

            timer.start();
        });
    }

    private static Optional<Timer> bestTimer(final int interval, final int numberTicks) {
        ServiceLoader<TimerProvider> loader = ServiceLoader.load(TimerProvider.class);
        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;

        for (TimerProvider provider : loader) {
            if (provider.timerResolution() < minResolution) {
                minResolution = provider.timerResolution();
                minProvider = provider;
            }
        }

        return minProvider == null ? Optional.empty() : Optional.of(minProvider.createTimer(interval, numberTicks));
    }
}
