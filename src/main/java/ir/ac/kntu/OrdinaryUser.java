package ir.ac.kntu;

import java.util.Scanner;

public class OrdinaryUser implements WorkingUser {
    private Hospital hospital;
    private Scanner scanner;
    private Patient patient;

    public OrdinaryUser(Scanner scanner, Hospital hospital) {
        this.scanner = scanner;
        this.hospital = hospital;
    }

    @Override
    public void startWorkLoop(Hospital myHospital) {
        clearScreen();
        findPatient();
        if (patient != null) {
            menu();
        } else {
            return;
        }
    }

    public void findPatient() {
        System.out.print("Enter National ID : ");
        int id = scanner.nextInt();
        if (hospital.getPatient(id) != null) {
            patient = hospital.getPatient(id);
        } else {
            System.out.println("No such a Patient found!");
        }
    }

    public void menu() {
        System.out.print("            Menu\n" +
                "--------------------------------\n" +
                "1.Your Information\n" +
                "2.Your Doctor's Shifts\n" +
                "3.Receive Bill\n" +
                "4.back\n" +
                "5.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        clearScreen();
        OrdinaryMenuChoose[] first = OrdinaryMenuChoose.values();
        if (choose >= 0 && choose < first.length) {
            switch (first[choose]) {
                case INFO:
                    printInformation();
                    break;
                case SHIFTS:
                    printShifts();
                    break;
                case BILL:
                    getBill();
                    break;
                case BACK:
                    return;
                case EXIT:
                    System.exit(0);
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }
        menu();
    }

    private void printShifts() {
        System.out.println("Doctor with ID : " + patient.getDoctorID());
        System.out.println("Shifts:" + hospital.getDoctorWithID(patient.getDoctorID()).shifts());
    }

    private void getBill() {
        System.out.println(patient.getBill());
    }

    private void printInformation() {
        System.out.println(patient.getInfo());
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

enum OrdinaryMenuChoose {
    INFO, SHIFTS, BILL, BACK, EXIT
}
