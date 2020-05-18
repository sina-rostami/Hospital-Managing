package ir.ac.kntu.classTest;

import ir.ac.kntu.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class RoomTest {
    private ArrayList<RoomEquipment> roomEquipments = new ArrayList<RoomEquipment>(
            Arrays.asList(new RoomEquipment(001), new RoomEquipment(002), new RoomEquipment(003))
    );
    private Room room = new Room(11, 5, true, 1000, roomEquipments);

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
    public void toStr() {
        assertEquals("ID : 11" +
                "\nisAvailable : true" +
                "\nNumber of Beds : 5" +
                "\nEquipments : Refrigerator, Tv, A/C" +
                "\nRoom Cost : 780.0000000000002" +
                "\nPatients : \n" , room.getRoomInfo());
    }

    @Test
    public void changeAvailability() {
        room.changeAvailability();
        assertFalse(room.isAvailable());
    }

    @Test
    public void getRoomCost() {
        assertEquals(780, (int)room.getRoomCost());
    }
}