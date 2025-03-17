package org.swt6.navigator;

@FunctionalInterface
public interface NavigatorListener {
    public void positionChanged(PositionChangedEvent event);
}
