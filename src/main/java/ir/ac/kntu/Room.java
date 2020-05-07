package ir.ac.kntu;

import java.util.ArrayList;

public class Room {
    private int id;
    private int numberOfBeds;
    private Boolean isAvailable;
    private ArrayList<Patient> patients = new ArrayList<>();
    private int firstBedCost;

    Room(int id, int numberOfBeds, Boolean isAvailable, int firstBedCost) {
        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.isAvailable = isAvailable;
        this.firstBedCost = firstBedCost;
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
        return firstBedCost * coefficient;
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

    public String getRoomInfo() {
        return "ID : " + id + "\n" +
                "isAvailable : " + isAvailable + "\n" +
                "Room Cost : " + getRoomCost() + "\n" +
                "Number of Beds : " + numberOfBeds + "\n" +
                "Patients : " + getPatientsIDs() + "\n";
    }
}
