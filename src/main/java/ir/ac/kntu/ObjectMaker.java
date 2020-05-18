package ir.ac.kntu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectMaker {
    private static Scanner scanner;
    private ArrayList<Part> parts;
    private Hospital hospital;

    public void setRequirements(Scanner scanner, ArrayList<Part> parts, Hospital hospital) {
        ObjectMaker.scanner = scanner;
        this.parts = parts;
        this.hospital = hospital;
    }

    public Doctor newDoctor(int id, int dID) {
        if (id != 0) {
            if(hospital.getDoctorWithID(id).getPatientsNumber() > 0) {
                System.out.println("Doctor has patients and cant be edited!");
                return null;
            }
            dID = id;
            hospital.deleteDoctor(hospital.getDoctorWithID(id));
        }
        System.out.print("Enter name of Doctor : ");
        String name = scanner.next();
        System.out.print("which part (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) :");
        int part = scanner.nextInt();
        if (part <= parts.size()) {
            System.out.print("Enter Max patients number ( 0 to set defaults) : ");
            int maxPatients = scanner.nextInt();
            if (maxPatients == 0) {
                maxPatients = part == 4 ? 3 : 5;
            }
            System.out.print("How Many Shifts does this Doctor has ? ");
            int counter = scanner.nextInt();
            System.out.println("Enter Shifts (eg: 1 2 (saturday shift 1)) : ");
            ArrayList<Shift> shifts = new ArrayList<>();
            Shift shift;
            for (int i = 0; i < counter; i++) {
                shift = new Shift(scanner.nextInt(), scanner.nextInt());
                if (!hasThisShift(shifts, shift.getDay(), shift.getShift()) &&
                        shift.getDay() > 0 && shift.getDay() < 8 && shift.getShift() > 0 && shift.getShift() < 4) {
                    shifts.add(shift);
                } else {
                    i--;
                }
            }
            return new Doctor(name, dID, shifts, part, maxPatients);
        } else {
            System.out.println("wrong inputs");
            return newDoctor(id, dID);
        }
    }

    public boolean hasThisShift(ArrayList<Shift> shifts, int day, int shift) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getDay() == day && shifts.get(i).getShift() == shift) {
                return true;
            }
        }
        return false;
    }

    public Nurse newNurse(int id, int nID) {
        if (id != 0) {
            nID = id;
            hospital.deleteNurse(hospital.getNurseWithID(id));
        }
        System.out.print("Enter name of Nurse : ");
        String name = scanner.next();
        System.out.print("which Part (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) : ");
        int part = scanner.nextInt();
        if (part <= parts.size()) {
            return new Nurse(name, nID, part);
        } else {
            System.out.println("wrong inputs");
            return newNurse(id, nID);
        }
    }

    public HospitalizationOrder newOrder() {
        System.out.print("Enter name of Patient : ");
        String name = scanner.next();
        System.out.print("Enter Part to Be Hospitalized (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) : ");
        int choose = scanner.nextInt();
        PartOfHospital[] part = PartOfHospital.values();
        PartOfHospital partOfHospital = null;
        if (choose <= parts.size()) {
            partOfHospital = part[choose - 1];
        } else {
            System.out.println("Wrong Input! Try Again...");
            newOrder();
        }
        System.out.print("Enter Illness Type (1(Burn), 2(Impact), 3(Accident), 4(Other)) : ");
        IllnessType illnessType = null;
        IllnessType[] i = IllnessType.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < i.length) {
            illnessType = i[choose];
        } else {
            System.out.println("Wrong Input! Try Again...");
            newOrder();
        }
        LocalDate setDate = Hospital.setDate();
        return new HospitalizationOrder(name, partOfHospital, illnessType, setDate);
    }

    public Patient newPatient(int id) {
        HospitalizationOrder order = newOrder();
        System.out.print("Enter National ID number : ");
        int nationalID = scanner.nextInt();
        if (hospital.getPatient(nationalID) != null && !hospital.getPatient(nationalID).isDisCharged()) {
            System.out.println("this National ID Already exists...");
            return hospital.getPatient(nationalID);
        }
        boolean wrongInputFlag = false;
        System.out.print("Enter Insurance Type (1(Tamin-Ejtemaei), 2(Niro-haye-Mosallah), 3(Khadamat-Darmani)) : ");
        Insurance insurance = null;
        int choose = scanner.nextInt() - 1;
        if (choose <= 3) {
            insurance = Insurance.values()[choose];
        } else {
            wrongInputFlag = true;
        }
        System.out.print("Enter age : ");
        int age = scanner.nextInt();
        System.out.print("Enter Human Kind (1(Male), 2(Female)) : ");
        HumanKind humanKind = HumanKind.MALE;
        HumanKind[] h = HumanKind.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < h.length) {
            humanKind = h[choose];
        } else {
            wrongInputFlag = true;
        }
        System.out.print("Enter Room Choose Strategy (1(midFull Room First), 2(Empty Room First)) : ");
        ChooseRoom chooseRoom = ChooseRoom.MID_FULL;
        ChooseRoom[] c = ChooseRoom.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < c.length) {
            chooseRoom = c[choose];
        } else {
            wrongInputFlag = true;
        }
        if (wrongInputFlag) {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            return null;
        }
        Doctor doctor = hospital.findDoctor(order.getToBeHospitalizedPart());
        if (doctor == null) {
            System.out.println("No Doctors Found !! ");
            return null;
        }
        return new Patient(id, order.getName(), nationalID, order.getIllnessType(), humanKind, age, doctor,
                order.getToBeHospitalizedPart(), insurance, order.getDate(), chooseRoom);
    }

    public SecurityMan newSecMan(int id) {
        System.out.print("Enter name : ");
        String name = scanner.next();
        return new SecurityMan(id, name, scanner);
    }

    public FacilityMan newFacMan(int id) {
        System.out.print("Enter name : ");
        String name = scanner.next();
        return new FacilityMan(id, name, scanner);
    }

}