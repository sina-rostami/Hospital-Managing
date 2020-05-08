package ir.ac.kntu;

import java.util.Scanner;

public class OrdinaryUser implements WorkingUser{
    private static Hospital hospital;
    private static Scanner scanner;
    private int nationalId;
    @Override
    public void startWorkLoop(Hospital myHospital) {
        hospital = myHospital;
        clearScreen();
        scanner = new Scanner(System.in);
        try {
            Menu();
        } finally {
            scanner.close();
        }
    }
    public void Menu() {
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
                    System.out.println("Wrong Input! try Again...");
                    Menu();
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
            Menu();
        }
    }

    private void printShifts() {
        System.out.println("Doctor with ID : " + hospital.getPatient(nationalId).getDoctorID());
        System.out.println("Shifts:"+hospital.getDoctorWithID(hospital.getPatient(nationalId).getDoctorID()).shifts());
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();
    }

    private void getBill() {
        System.out.println(hospital.getPatient(nationalId).getBill());
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();
    }

    private void printInformation() {
        System.out.println(hospital.getPatient(nationalId).getInfo());
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
enum OrdinaryMenuChoose {
    INFO, SHIFTS, BILL, BACK, EXIT
}
