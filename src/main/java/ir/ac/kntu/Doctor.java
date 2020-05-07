package ir.ac.kntu;

import java.util.*;

public class Doctor {
    private String name;
    private int id;
    private ArrayList<Shift> shifts = new ArrayList<Shift>();
    private ArrayList<Patient> presentPatients = new ArrayList<>();
    private ArrayList<Patient> allPatients = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private int maxPatientsNumber;
    private Boolean isDeleted = false;
    private int partId;

    public Doctor(String name, int id, ArrayList<Shift> shifts, int partId) {
        this.name = name;
        this.id = id;
        shiftsCopy(shifts);
        maxPatientsNumber = 5;
        this.partId = partId;
    }

    public void editDoctor(String name, ArrayList<Shift> shifts) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter max Patient number  : ");
        int n = scanner.nextInt();
        this.name = name;
        shiftsCopy(shifts);
        maxPatientsNumber = n;
    }

    public void setDeleted() {
        this.isDeleted = true;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public int getPartId() {
        return partId;
    }

    public void setNurse(Nurse nurse) {
        if (nurses.size() < 2 && !hasThisNurse(nurse)) {
            nurses.add(nurse);
        }
    }

    public boolean hasThisNurse(Nurse nurse) {
        for (Nurse n : nurses) {
            if (n.getId() == nurse.getId()) {
                return true;
            }
        }
        return false;
    }

    public void removeNurse(Nurse nurse) {
        nurses.remove(nurse);
    }

    public void clearNurses() {
        nurses.clear();
    }

    public ArrayList<Nurse> getNurses() {
        return new ArrayList<>(nurses);
    }

    public int getId() {
        return id;
    }

    public void addPatient(Patient p) {
        allPatients.add(p);
        presentPatients.add(p);
    }

    public void removePatient(Patient p) {
        presentPatients.remove(p);
    }

    public int getPatientsNumber() {
        return presentPatients.size();
    }

    public int getMaxPatientsNumber() {
        return maxPatientsNumber;
    }

    public int getFreeSpaceNumber() {
        return getMaxPatientsNumber() - getPatientsNumber();
    }

    public void addShift(int day, int shift) {
        if (day > 0 && day < 8 && shift > 0 && shift < 4 && shifts.size() < 3) {
            if (!hasThisShift(day, shift)) {
                Shift shift1 = new Shift(day, shift);
                this.shifts.add(shift1);
            }
        } else {
            return;
        }
    }

    public void remShift(int day, int shift) {
        if (day > 0 && day < 8 && shift > 0 && shift < 4) {
            for (int i = 0; i < shifts.size(); i++) {
                if (shifts.get(i).getDay() == day && shifts.get(i).getShift() == shift) {
                    shifts.remove(i);
                }
            }
        } else {
            return;
        }
    }

    public boolean hasThisShift(int day, int shift) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getDay() == day && shifts.get(i).getShift() == shift) {
                return true;
            }
        }
        return false;
    }

    public String shifts() {
        String shift = "";
        for (int i = 0; i < shifts.size(); i++) {
            shift += shifts.get(i).getDay() + "->" + shifts.get(i).getShift() + "   ";
        }
        return shift;
    }

    public void shiftsCopy(ArrayList<Shift> src) {
        this.shifts.clear();
        for (Shift s : src) {
            this.addShift(s.getDay(), s.getShift());
        }
    }

    public ArrayList<Shift> getShifts() {
        return new ArrayList<Shift>(shifts);
    }

    public ArrayList<Patient> getPresentPatients() {
        return new ArrayList<>(presentPatients);
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<>(allPatients);
    }

    public String nursesIDs() {
        String nurse = "";
        for (Nurse n : nurses) {
            nurse += n.getId() + "   ";
        }
        return nurse;
    }

    public String patientsIDs() {
        String patient = "";
        for (Patient p : presentPatients) {
            patient += p.getNationalID() + "  ";
        }
        return patient;
    }

    public String part() {
        if (partId == 1) {
            return "Ordinary";
        } else if (partId == 2) {
            return "Emergency";
        }
        return null;
    }

    public String getInfo() {
        return "\n" +
                "Name : " + name + "\n" +
                "ID : " + id + "\n" +
                "Part : " + part() + "\n" +
                "Shifts : " + this.shifts() + "\n" +
                "Nurses : " + this.nursesIDs() + "\n" +
                "Patients : " + this.patientsIDs() + "\n";
    }
}

