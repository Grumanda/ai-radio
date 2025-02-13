package de.gozilalp;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private int hour;
    private boolean isActivated;

    public Schedule(int hour, boolean isActivated) {
        this.hour = hour;
        this.isActivated = isActivated;
    }

    public void setActivated(boolean value) {
        isActivated = value;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public int getHour() {
        return hour;
    }
}