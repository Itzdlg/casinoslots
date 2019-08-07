package me.schooltests.slots.util;

import java.util.ArrayList;
import java.util.List;

public class DelayCounter {
    int currentDelay = 0;
    List<Integer> d = new ArrayList<Integer>();

    public void increment() {
        currentDelay++;
    }

    public int get() {
        return currentDelay;
    }

    public void setD(List<Integer> delays) {
        d = delays;
    }

    public List<Integer> getD() {
        return d;
    }
}
