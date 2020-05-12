package ir.ac.kntu.classTest;

import org.junit.Test;

import static org.junit.Assert.*;
import ir.ac.kntu.*;

public class ShiftTest {
    private Shift shift = new Shift(3,2);
    @Test
    public void getDay() {
        assertEquals(3, shift.getDay());
    }

    @Test
    public void getShift() {
        assertEquals(2, shift.getShift());
    }

    @Test
    public void testToString() {
        assertEquals("3->2", shift.toString());
    }
}