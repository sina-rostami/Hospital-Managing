package ir.ac.kntu;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

    private Room room = new Room(11, 5, true, 1000);

    @Test
    public void getFreeSpacesNum() {
        assertEquals(5, room.getFreeSpacesNum());
    }

    @Test
    public void getId() {
        assertEquals(11, room.getId());
    }

    @Test
    public void isAvailable() {
        assertTrue(room.isAvailable());
    }

    @Test
    public void changeAvailability() {
        room.changeAvailability();
        assertFalse(room.isAvailable());
    }

    @Test
    public void getRoomCost() {
        assertEquals(600, (int)room.getRoomCost());
    }
}