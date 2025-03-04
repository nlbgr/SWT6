package swt6.modular.client;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;

public class TimerClient {

    // java --module-path ".\modular-timer-beans\;.\modular-timer-client\" --module modular.timer.client/swt6.modular.client.TimerClient
    public static void main(String[] args) {
        testBeans();
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
    }

    private static void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ignored) { }
    }
}
