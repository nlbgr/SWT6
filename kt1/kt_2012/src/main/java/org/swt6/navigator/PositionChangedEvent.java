package org.swt6.navigator;

public class PositionChangedEvent {
    private double longitude;
    private double latitude;

    public PositionChangedEvent(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
