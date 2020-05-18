package ir.ac.kntu;

import java.util.*;

public class Main {
    private static Hospital hospital;
    private static Scanner scanner;
    public static void main(String[] args) {
        clearScreen();
        scanner = new Scanner(System.in);
        try{
            hospital = firstSet();
            while(true) {
                menu();
            }
        } catch (Exception e) {
            System.out.println("Something was wrong!run again\n");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    public static void menu() {
        System.out.print("        Welcome To Hospital Management\n" +
                "-----------------------------------------------\n" +
                "Who Are You ? \n" +
                "1.Administrator\n" +
                "2.Security Employee\n" +
                "3.Ordinary User \n" +
                "4.exit\n" +
                "Enter Your Choice :");
        int choose = scanner.nextInt() - 1;
        Menu[] menus = Menu.values();
        clearScreen();
        WorkingUser user = null;
        if(choose >= 0 && choose < menus.length) {
            switch (menus[choose]) {
                case ORD:
                    user = new OrdinaryUser(scanner, hospital);
                    break;
                case SEC:
                    user = new SecurityUser(hospital, scanner);
                    break;
                case ADMIN:
                    user = new Administrator(hospital, scanner);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
            user.startWorkLoop(hospital);
        } else {
            System.out.println("Wrong Input Try Again!");
            menu();
        }
    }
    public static Hospital firstSet() {
        System.out.print("Enter name of the Hospital : ");
        String hospitalName = scanner.next();
        System.out.print("Enter Address  of the Hospital : ");
        String hospitalAddress = scanner.next();
        System.out.print("Enter the Cost of a FirstBed room for a night : ");
        int firstBedCost = scanner.nextInt();
        System.out.print("Hospital is set :)");
        System.out.println("\npress enter to continue....");
        scanner.nextLine();
        clearScreen();
        return new Hospital(hospitalName, hospitalAddress, firstBedCost, scanner);
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static Boolean checkDate(int year, int month, int day) {
        if (year > 0 && month > 0 && day > 0) {
            return month < 13 && day < 32;
        }
        return false;
    }
}
enum Menu {
    ADMIN, SEC, ORD, EXIT
}