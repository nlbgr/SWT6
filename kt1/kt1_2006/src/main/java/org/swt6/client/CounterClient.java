package org.swt6.client;

import org.swt6.counter.Counter;

// https://drive.google.com/drive/folders/1lRQdKidxGYYC3NR-AV1VPgf-swtJREAN
public class CounterClient {
    public static void main(String[] args) {
        Counter c = new Counter(2);
        c.addListener(event -> {
            System.out.println(event.getCounter());
        });

        for (int i = 0; i < 10; i++) {
            c.increment();
        }
    }
}
