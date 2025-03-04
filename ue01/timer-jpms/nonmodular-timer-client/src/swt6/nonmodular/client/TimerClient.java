package swt6.nonmodular.client;

import swt6.nonmodular.beans.SimpleTimer;

public class TimerClient {

    public static void main(String[] args) {
        testBeans();
    }

    private static void testBeans() {
        final SimpleTimer timer = new SimpleTimer(10, 100);

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
