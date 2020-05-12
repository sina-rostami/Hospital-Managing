package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class FacilityMan {
    private String name;
    private int id;
    private ArrayList<Shift> shifts = new ArrayList<>();

    public FacilityMan(String name, int id, Scanner scanner) {
        this.name = name;
        this.id = id;
        setShifts(scanner);
    }

    public void setShifts(Scanner scanner) {
        System.out.print("How Many Shifts does this SecurityMan has ? ");
        int counter = scanner.nextInt();
        System.out.println("Enter Shifts (eg: 1 2 (saturday shift 1)) : ");
        for (int i = 0; i < counter; i++) {
            Shift shift = new Shift(scanner.nextInt(), scanner.nextInt());
            if (!hasThisShift(shift.getDay(), shift.getShift())) {
                shifts.add(shift);
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

    public String shifts() {
        String shift = "";
        for (int i = 0; i < shifts.size(); i++) {
            shift += shifts.get(i).getDay() + "->" + shifts.get(i).getShift() + "   ";
        }
        return shift;
    }

    @Override
    public String toString() {
        return "\nID : " + id + "\nName : " + name + "\nShifts : " + shifts() + "\n";
    }
}
