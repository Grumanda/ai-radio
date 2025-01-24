package de.gozilalp;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Boolean> schedule;

    public Schedule() {
        schedule = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            schedule.add(false);
        }
    }

    public boolean getScheduleAt(int hour) {
        return schedule.get(hour + 1);
    }

    public void setScheduleAt(int hour, boolean isOn) {
        schedule.set(hour + 1, isOn);
    }
}