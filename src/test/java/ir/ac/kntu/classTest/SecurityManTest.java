package ir.ac.kntu.classTest;

import ir.ac.kntu.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SecurityManTest {
    private ArrayList<Shift> shifts = new ArrayList<>(Arrays.asList(new Shift(1, 1),
            new Shift(1, 2), new Shift(3, 3)));
    private SecurityMan securityMan = new SecurityMan(100, "Ali", shifts);
    @Test
    public void getId() {
        assertEquals(100, securityMan.getId());
    }

    @Test
    public void testToString() {
        assertEquals("\nID : 100\nName : Ali\nShifts : " + shifts() + "\n", securityMan.toString());
    }

    public String shifts() {
        String shift = "";
        for (int i = 0; i < shifts.size(); i++) {
            shift += shifts.get(i).getDay() + "->" + shifts.get(i).getShift() + "   ";
        }
        return shift;
    }
}