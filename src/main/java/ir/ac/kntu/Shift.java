package ir.ac.kntu;

import java.util.ArrayList;

public class Shift {
    private int day;
    private int shift;

    public Shift(int day, int shift) {
        if (day > 0 && day < 8 && shift > 0 && shift < 4) {
            this.day = day;
            this.shift = shift;
        }
    }

    public int getDay() {
        return day;
    }

    public int getShift() {
        return shift;
    }

    public String toString() {
        return day + "->" + shift;
    }

}

