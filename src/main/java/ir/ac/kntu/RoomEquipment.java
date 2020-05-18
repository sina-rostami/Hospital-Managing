package ir.ac.kntu;

import java.time.LocalDate;

public class RoomEquipment {
    private int id;
    private boolean isOk;
    private LocalDate lastCheck;

    public RoomEquipment(int id) {
        this.id = id;
        lastCheck = LocalDate.now();
        isOk = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public LocalDate getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(LocalDate lastCheck) {
        this.lastCheck = lastCheck;
    }

    @Override
    public String toString() {
        return "ID : " + id +
                "\nis OK : " + isOk +
                "\nLast Check : " + lastCheck.toString();
    }
}
