package swt6.modular.beans;

import java.util.EventObject;

public class TimerEvent extends EventObject {

    private final int numberTicks;
    private final int tickCount;

    public TimerEvent(final Object source, final int numberTicks, final int tickCount) {
        super(source);
        this.numberTicks = numberTicks;
        this.tickCount = tickCount;
    }

    public int getNumberTicks() {
        return numberTicks;
    }

    public int getTickCount() {
        return tickCount;
    }
}
