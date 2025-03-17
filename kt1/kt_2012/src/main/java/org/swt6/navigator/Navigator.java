package org.swt6.navigator;

import java.util.ArrayList;
import java.util.List;

public class Navigator {
    private double longitude;
    private double latitude;
    private String orientation;

    private List<NavigatorListener> listeners = new ArrayList<NavigatorListener>();

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        onLocationChanged();
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        onLocationChanged();
        this.latitude = latitude;
    }

    private void onLocationChanged() {
        listeners.forEach(listener -> listener.positionChanged(new PositionChangedEvent(longitude, latitude)));
    }

    public String getOrientation() {
        return "North";
    }

    public void setOrientation(String orientation) {
        this.orientation = "South";
    }

    public void addListener(NavigatorListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NavigatorListener listener) {
        listeners.remove(listener);
    }
}
