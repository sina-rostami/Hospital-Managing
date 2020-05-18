package ir.ac.kntu.classTest;

import ir.ac.kntu.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartTest {
    private Part part = new Part(100, "Room");
    @Test
    public void getName() {
        assertEquals("Room", part.getName());
    }

    @Test
    public void getId() {
        assertEquals(100, part.getId());
    }
}