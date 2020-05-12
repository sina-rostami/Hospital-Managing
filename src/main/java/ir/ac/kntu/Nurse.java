package ir.ac.kntu;

import java.util.*;

public class Nurse {
    private String name;
    private int id;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Shift> shifts = new ArrayList<>();
    private Boolean isDeleted = false;
    private int partId;
    public Nurse(String name, int id, int partId) {
        this.name = name;
        this.id = id;
        this.partId = partId;
    }

    public void editNurse(Scanner scanner) {
        System.out.print("Name : ");
        name = scanner.nextLine();
        clearShifts();
        for (Doctor d : doctors) {
            shiftsCopy(this.shifts, d.getShifts());
        }
        setShifts(scanner);
    }

    public void setShifts(Scanner scanner) {
        if (shifts.size() < 6) {
            System.out.println("Enter " + (6 - shifts.size()) + " Shifts for nurse " +
                    id + " (eg: 1 2(saturday shift 1)) :");
            for (int i = shifts.size(); i < 6; i++) {
                Shift temp = new Shift(scanner.nextInt(), scanner.nextInt());
                if (!hasThisShift(temp.getDay(), temp.getShift()) &&
                        temp.getDay() > 0 && temp.getDay() < 8 && temp.getShift() > 0 && temp.getShift() < 4) {
                    shifts.add(new Shift(temp.getDay(), temp.getShift()));
                } else {
                    System.out.println("Wrong");
                    i--;
                }
            }
        }
    }

    public void setDoctor(Doctor doctor) {
        if (doctors.size() < 2 && !hasThisDoctor(doctor)) {
            doctors.add(doctor);
            for (Doctor d : doctors) {
                shiftsCopy(this.shifts, d.getShifts());
            }
        }
    }

    public boolean hasThisDoctor(Doctor doctor) {
        for (Doctor d : doctors) {
            if (d.getId() == doctor.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getPartId() {
        return partId;
    }

    public boolean hasThisShift(int day, int shift) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getDay() == day && shifts.get(i).getShift() == shift) {
                return true;
            }
        }
        return false;
    }

    public void clearDoctors() {
        doctors.clear();
        clearShifts();
    }

    public void setDeleted() {
        isDeleted = true;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void clearShifts() {
        shifts.clear();
    }

    public int getId() {
        return id;
    }

    public String shifts() {
        String shift = "";
        for (int i = 0; i < shifts.size(); i++) {
            shift += shifts.get(i).getDay() + "->" + shifts.get(i).getShift() + "   ";
        }
        return shift;
    }

    public void shiftsCopy(ArrayList<Shift> dest, ArrayList<Shift> src) {
        for (Shift s : src) {
            if (!hasThisShift(s.getDay(), s.getShift())) {
                dest.add(new Shift(s.getDay(), s.getShift()));
            }
        }
    }

    public ArrayList<Shift> getShifts() {
        return new ArrayList<>(shifts);
    }

    public String doctorsIDs() {
        String doctor = "";
        for (Doctor d : doctors) {
            doctor += d.getId() + "   ";
        }
        return doctor;
    }

    public PartOfHospital part() {
        return PartOfHospital.values()[partId - 1];
    }

    public String getInfo() {
        return "\n" +
                "Name : " + name + "\n" +
                "ID : " + id + "\n" +
                "Part : " + part() + "\n" +
                "Shifts : " + this.shifts() + "\n" +
                "Doctors : " + this.doctorsIDs() + "\n";
    }
}