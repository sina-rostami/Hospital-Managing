package ir.ac.kntu.classTest;

import org.junit.Test;
import ir.ac.kntu.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class RoomEquipmentTest {
    private RoomEquipment roomEquipment = new RoomEquipment(001);
    private LocalDate localDate = LocalDate.now();
    @Test
    public void getId() {
        assertEquals(1, roomEquipment.getId());
    }

    @Test
    public void isOk() {
        assertTrue(roomEquipment.isOk());
    }

    @Test
    public void testToString() {
        assertEquals("ID : 1" +
                "\nis OK : true" +
                "\nLast Check : " + localDate.toString(), roomEquipment.toString());
    }
}