package ir.ac.kntu;

import java.util.*;

public class Main {
    private static Hospital hospital = null;

    public static void main(String[] args) {
        clearScreen();
        Hospital h = firstSet();
        hospital = h;
        firstMenu();
    }

    public static void firstMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("            Menu\n" +
                "--------------------------------\n" +
                "1.Features And Performance menu\n" +
                "2.Status menu\n" +
                "3.Search menu\n" +
                "4.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;

        FirstMenuChoose[] first = FirstMenuChoose.values();
        clearScreen();
        if (choose >= 0 && choose < first.length) {
            switch (first[choose]) {
                case FEATURE:
                    featureMenu();
                    break;
                case STATUS:
                    statusMenu();
                    break;
                case SEARCH:
                    searchMenu();
                    break;
                case EXIT:
                    System.exit(0);
                default:
                    System.out.println("Wrong Input! try Again...");
                    firstMenu();
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
            firstMenu();
        }

    }

    public static void featureMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("            Features-Menu\n" +
                "----------------------------------------------\n" +
                "1.set, edit, see and discharge Patient\n" +
                "2.set, edit, see and remove Employee\n" +
                /*"3.set, edit, see and remove Part\n" +*/
                "3.set, edit, see and remove Room\n" +
                "4.go back to First-Menu\n" +
                "5.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        clearScreen();
        FeatureMenuChoose[] f = FeatureMenuChoose.values();
        if (choose >= 0 && choose < f.length) {
            switch (f[choose]) {
                case PATIENT_INFO:
                    patientFeatures();
                    break;
                case EMPLOYEE_INFO:
                    employeeFeatures();
                    break;
                /*case PART:
                    break;*/
                case ROOM:
                    roomFeatures();
                    break;
                case FIRST_MENU:
                    firstMenu();
                    break;
                case EXIT:
                    System.exit(0);
                default:
                    System.out.println("Wrong Input! try Again...");
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }
        featureMenu();
    }

    public static void patientFeatures() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Set, Edit or DisCharge a Patient\n" +
                "***************************************\n" +
                "1.Add new Patient\n" +
                "2.See or Edit Patient's Information\n" +
                "3.DisCharge Patient and See the Bill\n" + "4.Print All Patients Info\n" +
                "5.go back to the Feature-Menu\n" + "6.exit\n" + "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        PatientFeatures[] p = PatientFeatures.values();
        clearScreen();
        if (choose >= 0 && choose < p.length) {
            switch (p[choose]) {
                case ADD_PATIENT:
                    Patient patient = hospital.addAndGetPatient(hospital.addAndGetNewOrder());
                    if (patient != null) {
                        System.out.println("Patient Successfully Added With National ID : " + patient.getNationalID());
                    }
                    break;
                case EDIT_PATIENT:
                    System.out.print("Enter National ID of Patient : ");
                    int nationalId = scanner.nextInt();
                    for (Patient patient1 : hospital.getPatients()) {
                        if (nationalId == patient1.getNationalID()) {
                            System.out.print(patient1.getInfo());
                        }
                    }
                    patient = hospital.getPatient(nationalId);
                    if (patient != null) {
                        System.out.print("Enter 1(back), 2(Edit) :");
                        choose = scanner.nextInt();
                        if (choose == 2) {
                            patient.editPatient();
                            System.out.println("Information Changed Successfully!\n");
                        }
                    } else {
                        System.out.println("No such a patient found in hospital");
                    }
                    break;
                case DISCHARGE:
                    System.out.print("Enter National ID of Patient : ");
                    patient = hospital.getPatient(scanner.nextInt());
                    if (patient != null) {
                        hospital.removePatient(patient);
                    } else {
                        System.out.print("\nNo such A Patient Found\n");
                    }
                    break;
                case PRINT_ALL:
                    printAllPatients();
                    break;
                case FEATURE_MENU:
                    featureMenu();
                    break;
                case EXIT:
                    System.exit(0);
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! Try Again...");
        }

        patientFeatures();
    }

    public static int employeeFeaturesMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Set, edit, see or remove an Employee\n" +
                "************************************\n" +
                "1.Set New Doctor\n" +
                "2.Set New Nurse\n" +
                "3.See, Edit or Remove a Doctor\n" +
                "4.See, Edit or Remove a Nurse\n" +
                "5.Show All Doctors Information\n" +
                "6.Show All Nurses Information\n" +
                "7.go back to the Feature-Menu\n" +
                "8.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt();

        clearScreen();
        return choose;
    }

    public static void employeeFeatures() {
        Scanner scanner = new Scanner(System.in);
        switch (employeeFeaturesMenu()) {
            case 1:
                hospital.addOrEditDoctor(0);
                break;
            case 2:
                hospital.addNurse(0);
                break;
            case 3:
                System.out.print("Enter Doctor's ID : ");
                int id = scanner.nextInt();
                Doctor doctor = hospital.getDoctorWithID(id);
                if (doctor != null) {
                    System.out.println(doctor.getInfo());
                    System.out.print("Enter 1(edit) , 2(remove) or 3(back) : ");
                    int choose1 = scanner.nextInt();
                    if (choose1 == 1) {
                        hospital.addOrEditDoctor(id);
                    } else if (choose1 == 2) {
                        hospital.deleteDoctor(doctor);
                    }
                } else {
                    System.out.println("No Such a Doctor Found :(\n");
                }
                break;
            case 4:
                System.out.print("Enter Nurse's ID : ");
                id = scanner.nextInt();
                Nurse nurse = hospital.getNurseWithID(id);
                if (nurse != null) {
                    System.out.println(nurse.getInfo());
                    System.out.print("Enter 1(edit) , 2(remove) or 3(back) : ");
                    int choose1 = scanner.nextInt();
                    if (choose1 == 1) {
                        nurse.editNurse();
                    } else if (choose1 == 2) {
                        hospital.deleteNurse(nurse);
                    }
                } else {
                    System.out.println("No Such a Nurse  Found :(\n");
                }
                break;
            case 5:
                hospital.printAllDoctors();
                break;
            case 6:
                printAllNurses();
                break;
            case 7:
                featureMenu();
                break;
            case 8:
                System.exit(0);
                ;
            default:
                System.out.println("Wrong Input! Try Again...\n");
                break;
        }

        employeeFeatures();
    }

    public static int roomFeaturesMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("set, edit, see and remove Room\n" +
                "**********************************\n" +
                "1.Set new Room\n" +
                "2.See, Edit or remove a Room\n" +
                "3.Print All rooms info\n" +
                "4.go back to the Features Menu\n" +
                "5.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;

        clearScreen();
        return choose;
    }

    public static void roomFeatures() {
        Scanner scanner = new Scanner(System.in);
        int choose = roomFeaturesMenu();
        RoomFeature[] r = RoomFeature.values();
        if (choose >= 0 && choose < r.length) {
            switch (r[choose]) {
                case SET:
                    hospital.addNewRoom();
                    roomFeatures();
                    break;
                case S_E_R:
                    scanner = new Scanner(System.in);
                    Room room = hospital.getRoom();
                    System.out.println(room.getRoomInfo());
                    System.out.println("Enter Choose (1(back), 2(edit), 3(set un/Available)) : ");
                    choose = scanner.nextInt();
                    clearScreen();
                    switch (choose) {
                        case 2:
                            System.out.print("Enter Number Of Beds : ");
                            if (room.editRoom(scanner.nextInt())) {
                                System.out.println("Room Changed Successfully :)");
                            } else {
                                System.out.println("Room Can not be Changed Because number of Patients in Room" +
                                        "is more than your entered number  :)");
                            }
                            break;
                        case 3:
                            if (room.changeAvailability()) {
                                System.out.println("Room Availability Changed Successfully :) \n");
                            } else {
                                System.out.println("Room Availability Can not be Changed Because there Are" +
                                        " Patients in Room Discharge them First :) \n");
                            }
                            break;
                        default:
                            System.out.println("Wrong Input! Try Again \n");
                            break;
                    }
                    break;
                case PRINT_ALL:
                    printAllRooms();
                    break;
                case BACK:
                    featureMenu();
                    break;
                case EXIT:
                    System.exit(0);
                    ;
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! Try Again...");
        }

        roomFeatures();
    }

    public static void printAllNurses() {
        clearScreen();
        for (Nurse n : hospital.getNurses()) {
            if (!n.isDeleted()) {
                System.out.println(n.getInfo() + "\n");
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();

    }

    public static void printAllPatients() {
        clearScreen();
        for (Patient p : hospital.getPatients()) {
            if (!p.isDisCharged()) {
                System.out.println(p.getInfo());
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();

    }

    public static void printAllRooms() {
        System.out.println("<< Ordinary >>\n");
        for (Room r : hospital.getOrdinaryRooms()) {
            System.out.println(r.getRoomInfo());
        }
        System.out.println("\n<< Emergency : >>\n");
        for (Room r : hospital.getEmergencyRooms()) {
            System.out.println(r.getRoomInfo());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("press Enter to go back to previous Menu...");
        scanner.nextLine();

    }

    public static void statusMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("            Status-Menu\n" +
                "--------------------------------------------------------\n" +
                "1.see Patients of a Part\n" +
                "2.see Patients of a Doctor in a Specified Interval\n" +
                "3.see Income of Hospital in a Specified Interval\n" +
                "4.see history of Patients in a Specified Interval\n" +
                "5.see all Shifts of a Part\n" +
                "6.go back to the First-Menu\n" +
                "7.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        clearScreen();
        StatusMenuChoose[] s = StatusMenuChoose.values();
        if (choose >= 0 && choose < s.length) {
            switch (s[choose]) {
                case PATIENTS_OF_PART:
                    hospital.printPartPatients();
                    break;
                case PATIENTS_OF_DOCTOR:
                    hospital.printPatientsOfDr();
                    break;
                case INCOME:
                    hospital.printIncomeInterval();
                    break;
                case PATIENTS_OF_HOSPITAL:
                    hospital.printPatientsIntv();
                    break;
                case SHIFTS_OF_PART:
                    System.out.print("which part (1(Ordinary), 2(Emergency)) : ");
                    int partId = scanner.nextInt();
                    if (partId == 1 || partId == 2) {
                        hospital.printShiftsOfPart(partId);
                    }
                    break;
                case FIRST_MENU:
                    firstMenu();
                    break;
                case EXIT:
                    System.exit(0);
                    ;
                default:
                    System.out.println("Wrong Input! try Again...");
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }

        statusMenu();
    }

    public static void searchMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("            Search-Menu\n" +
                "--------------------------------------------------------------\n" +
                /*"1.Search Empty Room in a Specified Interval\n" +
                "2.Search Room in a Specified Interval Based on Its Free Beds\n" +*/
                "1.unAvailable Rooms\n" +
                "2.Search Doctor, Nurse or Both in a Specified Shift\n" +
                /*"5.Search Nurses with shift of a Specified Doctor\n" +
                "6.Search Nurses that was in hospital while a patient was there(with NationalId or id)\n" +*/
                "3.go back to the First-Menu\n" +
                "4.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        SearchMenuChoose[] s = SearchMenuChoose.values();
        clearScreen();
        if (choose >= 0 && choose < s.length) {
            switch (s[choose]) {
                /*case EMPTY_ROOM:
                    break;
                case S_ROOM:
                    break;*/
                case UNAVAILABLE_ROOM:
                    hospital.printUnAvailableRooms();
                    break;
                case D_N_OF_SHIFT:
                    hospital.printEmployeeOfShift();
                    break;
                /*case NURSES_OF_SHIFT:
                    break;
                case NURSES_OF_PATIENT:
                    break;*/
                case FIRST_MENU:
                    firstMenu();
                    break;
                case EXIT:
                    System.exit(0);
                    ;
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }

        searchMenu();
    }

    public static Hospital firstSet() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name of the Hospital : ");
        String hospitalName = scanner.nextLine();
        System.out.print("Enter Address  of the Hospital : ");
        String hospitalAddress = scanner.nextLine();
        System.out.print("Enter the Cost of a FirstBed room for a night : ");
        int firstBedCost = scanner.nextInt();
        System.out.print("Hospital is set :)");
        scanner = new Scanner(System.in);
        System.out.print("\npress enter to continue....");
        scanner.nextLine();
        clearScreen();
        return new Hospital(hospitalName, hospitalAddress, firstBedCost);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Boolean checkDate(int year, int month, int day) {
        if (year > 0 && month > 0 && day > 0) {
            if (month < 13 && day < 32) {
                return true;
            }
        }
        return false;
    }
}

enum ChooseRoom {
    MIDFULL, EMPTY;
}

enum FirstMenuChoose {
    FEATURE, STATUS, SEARCH, EXIT;
}

enum FeatureMenuChoose {
    PATIENT_INFO, EMPLOYEE_INFO, /*PART,*/ ROOM, FIRST_MENU, EXIT;
}

enum PatientFeatures {
    ADD_PATIENT, EDIT_PATIENT, DISCHARGE, PRINT_ALL, FEATURE_MENU, EXIT;
}

enum RoomFeature {
    SET, S_E_R, PRINT_ALL, BACK, EXIT;
}

enum SearchMenuChoose {
    /*EMPTY_ROOM, S_ROOM,*/ UNAVAILABLE_ROOM, D_N_OF_SHIFT,/* NURSES_OF_SHIFT,
    NURSES_OF_PATIENT,*/ FIRST_MENU, EXIT;
}

enum StatusMenuChoose {
    PATIENTS_OF_PART, PATIENTS_OF_DOCTOR, INCOME,
    PATIENTS_OF_HOSPITAL, SHIFTS_OF_PART, FIRST_MENU, EXIT;
}