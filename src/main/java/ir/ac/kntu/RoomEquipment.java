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

    public void setOk() {
        if(isOk) {
            isOk = false;
        } else {
            isOk = true;
        }
    }

    public LocalDate getLastCheck() {
        return lastCheck;
    }

    public void setLastCheck(LocalDate lastCheck) {
        this.lastCheck = lastCheck;
    }

    public double getCoe() {
        if (getId() == 001) {
            return 0.1;
        } else if (getId() == 002) {
            return 0.15;
        } else if (getId() == 003) {
            return 0.05;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "ID : " + id +
                "\nis OK : " + isOk +
                "\nLast Check : " + lastCheck.toString();
    }
}
