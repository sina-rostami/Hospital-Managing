package ir.ac.kntu;

import java.util.ArrayList;

public class Room {
    private int id;
    private int numberOfBeds;
    private Boolean isAvailable;
    private ArrayList<Patient> patients = new ArrayList<>();
    private int firstBedCost;
    private ArrayList<RoomEquipment> roomEquipments = new ArrayList<>();
    private boolean isVIP;

    public Room(int id, int numberOfBeds, Boolean isAvailable, int firstBedCost, ArrayList<RoomEquipment> equips) {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.isAvailable = isAvailable;
        this.firstBedCost = firstBedCost;
        this.roomEquipments = equips;
        if(numberOfBeds == 1) {
            this.isVIP = true;
        }
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        isVIP = isVIP;
    }

    public Boolean editRoom(int numberOfBeds) {
        if (patients.size() <= numberOfBeds) {
            this.numberOfBeds = numberOfBeds;
            return true;
        }
        return false;
    }

    public int getPatientsNum() {
        return patients.size();
    }

    public int getFreeSpacesNum() {
        return numberOfBeds - getPatientsNum();
    }

    public int getId() {
        return id;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public Boolean changeAvailability() {
        if (patients.size() != 0) {
            return false;
        }
        if (isAvailable) {
            isAvailable = false;
        } else {
            isAvailable = true;
        }
        return true;
    }

    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public double getRoomCost() {
        double coefficient = 1;
        if (this.numberOfBeds == 1) {
            return firstBedCost;
        }
        for (int i = 1; i < this.numberOfBeds; i++) {
            coefficient -= 0.1;
        }
        double equipCoe = 1;
        if (roomEquipments.size() > 0) {
            for (RoomEquipment r : roomEquipments) {
                if(r.isOk()) {
                    equipCoe += r.getCoe();
                }
            }
        }
        return (firstBedCost * coefficient) * equipCoe;
    }

    public String getPatientsIDs() {
        String s = "";
        if (patients.size() > 0) {
            for (Patient p : patients) {
                s += p.getNationalID() + "   ";
            }
        }
        return s;
    }

    public String equips() {
        String equ = "";
        if (roomEquipments.size() > 0) {
            for (RoomEquipment r : roomEquipments) {
                if(r.isOk()) {
                    if (r.getId() == 001) {
                        equ += "Refrigerator, ";
                    } else if (r.getId() == 002) {
                        equ += "Tv, ";
                    } else if (r.getId() == 003) {
                        equ += "A/C";
                    }
                }
            }
        }
        return equ;
    }

    public ArrayList<RoomEquipment> getRoomEquipments() {
        return new ArrayList<>(roomEquipments);
    }

    public String getRoomInfo() {
        return "ID : " + id +
                "\nisAvailable : " + isAvailable +
                "\nNumber of Beds : " + numberOfBeds +
                "\nEquipments : " + equips() +
                "\nRoom Cost : " + getRoomCost() +
                "\nPatients : " + getPatientsIDs() + "\n";
    }
}
