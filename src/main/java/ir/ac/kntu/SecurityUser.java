package ir.ac.kntu;

import java.util.Scanner;

public class SecurityUser implements WorkingUser {
    private Hospital hospital;
    private Scanner scanner;

    public SecurityUser(Hospital hospital, Scanner scanner) {
        this.hospital = hospital;
        this.scanner = scanner;
    }

    @Override
    public void startWorkLoop(Hospital myHospital) {
        clearScreen();
        menu();
    }

    public void menu() {
        System.out.print("            Menu\n" +
                "--------------------------------\n" +
                "1.Patients Information\n" +
                "2.Employees Information\n" +
                "3.Rooms Information\n" +
                "4.back\n" +
                "5.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        clearScreen();
        SecurityMenuChoose[] first = SecurityMenuChoose.values();
        if (choose >= 0 && choose < first.length) {
            switch (first[choose]) {
                case PATIENT:
                    printAllPatients();
                    break;
                case EMPLOYEE:
                    printAllDoctors();
                    printAllNurses();
                    break;
                case ROOM:
                    printAllRooms();
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

    public void printAllNurses() {
        System.out.println("Nurses : ");
        for (Nurse n : hospital.getNurses()) {
            if (!n.isDeleted()) {
                System.out.println(n.getInfo() + "\n");
            }
        }
    }

    public void printAllDoctors() {
        clearScreen();
        System.out.println("Doctors : ");
        for (Doctor d : hospital.getDoctors()) {
            if (!d.isDeleted()) {
                System.out.println(d.getInfo() + "\n");
            }
        }
        System.out.println();
    }

    public void printAllPatients() {
        clearScreen();
        for (Patient p : hospital.getPatients()) {
            if (!p.isDisCharged()) {
                System.out.println(p.getInfo());
            }
        }
    }

    public void printAllRooms() {
        System.out.println("<< Ordinary >>\n");
        for (Room r : hospital.getOrdinaryRooms()) {
            System.out.println(r.getRoomInfo());
        }
        System.out.println("\n<< Emergency : >>\n");
        for (Room r : hospital.getEmergencyRooms()) {
            System.out.println(r.getRoomInfo());
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

enum SecurityMenuChoose {
    PATIENT, EMPLOYEE, ROOM, BACK, EXIT
}
