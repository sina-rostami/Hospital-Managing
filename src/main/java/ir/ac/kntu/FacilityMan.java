package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class FacilityMan {
    private String name;
    private int id;
    private ArrayList<Shift> shifts = new ArrayList<>();
    private boolean isBusy;

    public FacilityMan(int id, String name, Scanner scanner) {
        this.name = name;
        this.id = id;
        this.isBusy = false;
        setShifts(scanner);
        System.out.println("Added successfully!");
    }
    public FacilityMan(int id, String name, ArrayList<Shift> shifts) {
        this.name = name;
        this.id = id;
        this.shifts = shifts;
    }

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy() {
        if(isBusy) {
            isBusy = false;
        } else {
            isBusy = true;
        }
    }

    public void setShifts(Scanner scanner) {
        System.out.print("How Many Shifts does this SecurityMan has ? ");
        int counter = scanner.nextInt();
        System.out.println("Enter Shifts (eg: 1 2 (saturday shift 1)) : ");
        for (int i = 0; i < counter; i++) {
            Shift shift = new Shift(scanner.nextInt(), scanner.nextInt());
            if (!hasThisShift(shift.getDay(), shift.getShift()) &&
                    shift.getDay() > 0 && shift.getDay() < 8 && shift.getShift() > 0 && shift.getShift() < 4) {
                shifts.add(shift);
            } else {
                System.out.println("Wrong");
                i--;
            }
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

    public boolean hasThisShift(int day) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getDay() == day ) {
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

    public int getCloserShift(int day) {
        int i = 0, c;
        for(i =  day, c = 0; c < 7; i++, i %= 7, c++) {
            if(hasThisShift(i)) {
                break;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return "\nID : " + id + "\nName : " + name + "\nShifts : " + shifts() + "\n";
    }
}
