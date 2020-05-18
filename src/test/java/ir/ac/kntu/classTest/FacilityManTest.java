package ir.ac.kntu.classTest;

import ir.ac.kntu.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FacilityManTest {
    private ArrayList<Shift> shifts = new ArrayList<>(Arrays.asList(new Shift(1, 1),
            new Shift(1, 2), new Shift(3, 3)));
    private FacilityMan facilityMan = new FacilityMan(100, "Ali", shifts);
    @Test
    public void getId() {
        assertEquals(100, facilityMan.getId());
    }

    @Test
    public void testToString() {
        assertEquals("\nID : 100\nName : Ali\nShifts : " + shifts() + "\n", facilityMan.toString());
    }

    @Test
    public void testGetCloser1() {
        assertEquals(1, facilityMan.getCloserShift(1));
    }

    @Test
    public void testGetCloser2() {
        assertEquals(3, facilityMan.getCloserShift(3));
    }

    @Test
    public void testGetCloser3() {
        assertEquals(3, facilityMan.getCloserShift(2));
    }

    @Test
    public void testGetCloser4() {
        assertEquals(1, facilityMan.getCloserShift(4));
    }

    public String shifts() {
        String shift = "";
        for (int i = 0; i < shifts.size(); i++) {
            shift += shifts.get(i).getDay() + "->" + shifts.get(i).getShift() + "   ";
        }
        return shift;
    }
}