package org.swt6.client;

import org.swt6.navigator.Navigator;

// https://drive.google.com/file/d/1wNA0dc-cIoSulGw3MFoUp3FHg7l6Bd7H/view?usp=drive_link;
public class NavigatorClient {
    public static void main(String[] args) {
        Navigator nav = new Navigator();
        nav.addListener(event -> {
            System.out.printf("%f, %f", event.getLatitude(), event.getLongitude());
        });

        // ...
    }
}
