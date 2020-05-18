package ir.ac.kntu;

import java.util.Scanner;

public class Administrator implements WorkingUser {
    private Hospital hospital;
    private Scanner scanner;

    public Administrator(Hospital hospital, Scanner scanner) {
        this.hospital = hospital;
        this.scanner = scanner;
    }

    @Override
    public void startWorkLoop(Hospital myHospital) {
        clearScreen();
        firstMenu();
    }
    public void firstMenu() {
        System.out.print("            Menu\n" +
                "--------------------------------\n" +
                "1.Features And Performance menu\n" +
                "2.Status menu\n" +
                "3.Search menu\n" +
                "4.back\n" +
                "5.exit\n" +
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
        firstMenu();
    }
    public void featureMenu() {
        System.out.print("            Features-Menu\n" +
                "----------------------------------------------\n" +
                "1.set, edit, see and discharge Patient\n" +
                "2.set, edit, see and remove Employee\n" +
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
                case ROOM:
                    roomFeatures();
                    break;
                case FIRST_MENU:
                    return;
                case EXIT:
                    System.exit(0);
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }
        featureMenu();
    }
    public void patientFeatures() {
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
                    Patient patient = hospital.addAndGetPatient();
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
                            patient.editPatient(scanner);
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
                    return;
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
    public int employeeFeaturesMenu() {
        System.out.print("Set, edit, see or remove an Employee\n" +
                "************************************\n" +
                "1.Set New Doctor\n" +
                "2.Set New Nurse\n" +
                "3.Set New SecurityMan\n" +
                "4.Set New FacilityMan\n" +
                "5.See, Edit or Remove a Doctor\n" +
                "6.See, Edit or Remove a Nurse\n" +
                "7.See, Edit or Remove a SecurityMan\n" +
                "8.See, Edit or Remove a FacilityMan\n" +
                "9.Show All Doctors Information\n" +
                "10.Show All Nurses Information\n" +
                "11.Show All SecurityMan Information\n" +
                "12.Show All FacilityMan Information\n" +
                "13.go back to the Feature-Menu\n" +
                "14.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt();
        clearScreen();
        return choose;
    }
    public void employeeFeatures() {
        switch (employeeFeaturesMenu()) {
            case 1:
                hospital.addOrEditDoctor(0);
                break;
            case 2:
                hospital.addNurse(0);
                break;
            case 3:
                hospital.addSecMan(0);
                break;
            case 4:
                hospital.addFacMan(0);
                break;
            case 5:
                hospital.seeDoctor();
                break;
            case 6:
                hospital.seeNurse();
                break;
            case 7:
                hospital.seeSecMan();
                break;
            case 8:
                System.out.print("Enter ID : ");
                int id = scanner.nextInt();
                FacilityMan fMan = hospital.getFacMan(id);
                if(fMan != null) {
                    System.out.println(fMan.toString() + "\nEnter 1(edit) , 2(remove) or 3(back) : ");
                    int choose = scanner.nextInt();
                    if (choose == 1) {
                        hospital.addFacMan(fMan.getId());
                    } else if (choose == 2) {
                        hospital.deleteFacMan(fMan);
                    }
                } else {
                    System.out.println("Not Found !");
                }
                break;
            case 9:
                hospital.printAllDoctors();
                break;
            case 10:
                hospital.printAllNurses();
                break;
            case 11:
                //hospital.printAllSecMen();
                break;
            case 12:
                hospital.printAllFacMan();
                break;
            case 13:
                return;
            case 14:
                System.exit(0);
            default:
                System.out.println("Wrong Input! Try Again...\n");
                break;
        }
        employeeFeatures();
    }
    public int roomFeaturesMenu() {
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
    public void roomFeatures() {
        int choose = roomFeaturesMenu();
        RoomFeature[] r = RoomFeature.values();
        if (choose >= 0 && choose < r.length) {
            switch (r[choose]) {
                case SET:
                    hospital.addNewRoom();
                    break;
                case S_E_R:
                    Room room = hospital.getRoom();
                    if (room == null) {
                        System.out.println("No Such a Room Found!");
                        break;
                    }
                    System.out.println(room.getRoomInfo());
                    System.out.println("Enter Choose (1(back), 2(edit), 3(set un/Available), 4(change equip damage)):");
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
                        case 4:
                            System.out.print(room.equips() + "\nWhich one (num) ?");
                            hospital.damageReport(room, scanner.nextInt());
                            break;
                        default:
                            System.out.println("Wrong Input! Try Again \n");
                            break;
                    }
                    break;
                case PRINT_ALL:
                    hospital.printAllRooms();
                    break;
                case BACK:
                    return;
                case EXIT:
                    System.exit(0);
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! Try Again...");
        }
        roomFeatures();
    }
    public void printAllPatients() {
        clearScreen();
        for (Patient p : hospital.getPatients()) {
            if (!p.isDisCharged()) {
                System.out.println(p.getInfo());
            }
        }
    }
    public void statusMenu() {
        System.out.print("            Status-Menu\n" +
                "--------------------------------------------------------\n" +
                "1.see Patients of a Part\n" +
                "2.see Patients of a Doctor in a Specified Interval\n" +
                "3.see Income of Hospital in a Specified Interval\n" +
                "4.see history of Patients in a Specified Interval\n" +
                "5.see all Shifts of a Part\n" +
                "6.back\n" +
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
                    int partId = scanner.nextInt() - 1;
                    if (partId > 0 && partId < 4) {
                        hospital.printShiftsOfPart(partId);
                    }
                    break;
                case FIRST_MENU:
                    return;
                case EXIT:
                    System.exit(0);
                default:
                    System.out.println("Wrong Input! try Again...");
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }
        statusMenu();
    }
    public void searchMenu() {
        System.out.print("            Search-Menu\n" +
                "--------------------------------------------------------------\n" +
                "1.unAvailable Rooms\n" +
                "2.Search Doctor, Nurse or Both in a Specified Shift\n" +
                "3.back\n" +
                "4.exit\n" +
                "Enter the number of choose : ");
        int choose = scanner.nextInt() - 1;
        SearchMenuChoose[] s = SearchMenuChoose.values();
        clearScreen();
        if (choose >= 0 && choose < s.length) {
            switch (s[choose]) {
                case UNAVAILABLE_ROOM:
                    hospital.printUnAvailableRooms();
                    break;
                case D_N_OF_SHIFT:
                    hospital.printEmployeeOfShift();
                    break;
                case FIRST_MENU:
                    return;
                case EXIT:
                    System.exit(0);
                default:
                    break;
            }
        } else {
            System.out.println("Wrong Input! try Again...");
        }
        searchMenu();
    }
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
enum FirstMenuChoose {
    FEATURE, STATUS, SEARCH, BACK, EXIT
}
enum FeatureMenuChoose {
    PATIENT_INFO, EMPLOYEE_INFO, ROOM, FIRST_MENU, EXIT
}
enum PatientFeatures {
    ADD_PATIENT, EDIT_PATIENT, DISCHARGE, PRINT_ALL, FEATURE_MENU, EXIT
}
enum RoomFeature {
    SET, S_E_R, PRINT_ALL, BACK, EXIT
}
enum SearchMenuChoose {
    UNAVAILABLE_ROOM, D_N_OF_SHIFT, FIRST_MENU, EXIT
}
enum StatusMenuChoose {
    PATIENTS_OF_PART, PATIENTS_OF_DOCTOR, INCOME,PATIENTS_OF_HOSPITAL, SHIFTS_OF_PART, FIRST_MENU, EXIT
}
