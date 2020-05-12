package ir.ac.kntu;
import java.time.LocalDate;
import java.util.*;
public class Hospital {
    private String name;
    private String address;
    private int firstBedCost;
    private Part ordinary = new Part(1, "Ordinary");
    private Part emergency = new Part(2, "Emergency");
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private ArrayList<HospitalizationOrder> orders = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<SecurityMan> securityMEN = new ArrayList<>();
    private ArrayList<FacilityMan> facilityMEN  = new ArrayList<>();
    private int patientIDs = 11111;
    private int doctorIDs = 111;
    private int nurseIDs = 111;
    private double income = 0;
    private static Scanner scanner;
    public Hospital(String name, String address, int firstBedCost, Scanner scanner) {
        this.name = name;
        this.address = address;
        this.firstBedCost = firstBedCost;
        this.scanner = scanner;
    }
    public Doctor addOrEditDoctor(int id) {
        Doctor d;
        System.out.print("Enter name of Doctor : ");
        String name = scanner.next();
        int part = 0;
        if (id == 0) {
            System.out.print("which part (1(Ordinary), 2(Emergency)) :");
            part = scanner.nextInt();
        }
        System.out.print("How Many Shifts does this Doctor has ? ");
        int counter = scanner.nextInt();
        System.out.println("Enter Shifts (eg: 1 2 (saturday shift 1)) : ");
        ArrayList<Shift> shifts1 = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            shifts1.add(new Shift(scanner.nextInt(), scanner.nextInt()));
        }
        if (id == 0) {
            d = new Doctor(name, doctorIDs++, shifts1, part);
            if (part == 1) {
                ordinary.addDoctor(d);
            } else if (part == 2) {
                emergency.addDoctor(d);
            } else {
                addOrEditDoctor(0);
            }
            doctors.add(d);
            System.out.println("Doctor Added Successfully with ID : " + d.getId() + "\n");
        } else {
            d = getDoctorWithID(id);
            d.editDoctor(name, shifts1, scanner);
        }
        autoSet(d.getPartId());
        return d;
    }
    public Doctor getDoctorWithID(int id) {
        for (Doctor d : doctors) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }
    public void deleteDoctor(Doctor d) {
        d.setDeleted();
        d.clearNurses();
        if (d.getPartId() == 1) {
            ordinary.removeDoctor(d);
        } else if (d.getPartId() == 2) {
            emergency.removeDoctor(d);
        }
        autoSet(d.getPartId());
        System.out.println("Doctor with ID : " + d.getId() + " Successfully Deleted!\n");
    }
    public void printAllDoctors() {
        Main.clearScreen();
        for (Doctor d : doctors) {
            if (!d.isDeleted()) {
                System.out.println(d.getInfo() + "\n");
            }
        }
    }
    public ArrayList<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }
    public Nurse addNurse(int id) {
        Nurse n = null;
        System.out.print("Enter name of Nurse : ");
        String name = scanner.next();
        System.out.print("which Part (1(Ordinary), 2(Emergency)) : ");
        int part = scanner.nextInt();
        if (id == 0) {
            if (part == 1) {
                n = new Nurse(name, nurseIDs++, 1);
                ordinary.addNurse(n);
            } else if (part == 2) {
                n = new Nurse(name, nurseIDs++, 2);
                emergency.addNurse(n);
            } else {
                addNurse(0);
            }
            nurses.add(n);
            System.out.println("Nurse Added Successfully with ID : " + n.getId() + "\n");
        }
        autoSet(part);
        return n;
    }
    public Nurse getNurseWithID(int id) {
        for (Nurse n : nurses) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }
    public void deleteNurse(Nurse n) {
        n.setDeleted();
        n.clearDoctors();
        if (n.getPartId() == 1) {
            ordinary.removeNurse(n);
        } else if (n.getPartId() == 2) {
            emergency.removeNurse(n);
        }
        autoSet(n.getPartId());
        System.out.println("Nurse with ID : " + n.getId() + " Successfully Deleted!\n");
    }
    public ArrayList<Nurse> getNurses() {
        return new ArrayList<>(nurses);
    }
    public void printEmployeeOfShift() {
        System.out.print("Enter Shift like this ( 1 1 for (day 1 - shift 1)) : ");
        Shift shift = new Shift(scanner.nextInt(), scanner.nextInt());
        System.out.print("Doctors(1) Nurses(2) Both(3) : ");
        int choose = scanner.nextInt();
        if (choose == 1 || choose == 3) {
            System.out.println("<< Doctors >>");
            for (Doctor d : doctors) {
                if (d.hasThisShift(shift.getDay(), shift.getShift()) && !d.isDeleted()) {
                    System.out.print(d.getId() + "  ");
                }
            }
            System.out.println();
        }
        if (choose == 2 || choose == 3) {
            System.out.println("<< Nurses >>");
            for (Nurse n : nurses) {
                if (n.hasThisShift(shift.getDay(), shift.getShift()) && !n.isDeleted()) {
                    System.out.print(n.getId() + "  ");
                }
            }
            System.out.println();
        }
    }
    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(patients);
    }
    public HospitalizationOrder addAndGetNewOrder() {
        System.out.print("Enter name of Patient : ");
        String name = scanner.next();
        System.out.print("Enter Part to Be Hospitalized (1(Emergency), 2(Ordinary)) : ");
        int choose = scanner.nextInt();
        PartOfHospital partOfHospital = PartOfHospital.EMERGENCY;
        switch (choose) {
            case 1:
                partOfHospital = PartOfHospital.EMERGENCY;
                break;
            case 2:
                partOfHospital = PartOfHospital.ORDINARY;
                break;
            default:
                System.out.println("Wrong Input! Try Again...");
                addAndGetNewOrder();
                break;
        }
        System.out.print("Enter Illness Type (1(Burn), 2(Impact), 3(Accident), 4(Other)) : ");
        IllnessType illnessType = IllnessType.OTHER;
        IllnessType[] i = IllnessType.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < i.length) {
            illnessType = i[choose];
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            addAndGetNewOrder();
        }
        LocalDate setDate = setDate();
        HospitalizationOrder order = new HospitalizationOrder(name, partOfHospital, illnessType, setDate);
        System.out.println("Order Successfully Added : )");
        orders.add(order);
        return order;
    }
    public Patient addAndGetPatient(HospitalizationOrder order) {
        System.out.print("Enter National ID number : ");
        int nationalID = scanner.nextInt();
        if (getPatient(nationalID) != null && !getPatient(nationalID).isDisCharged()) {
            System.out.println("this National ID Already exists...");
            return getPatient(nationalID);
        }
        boolean wrongInputFlag = false;
        System.out.print("Enter Insurance Type (1(Tamin-Ejtemaei), 2(Niro-haye-Mosallah), 3(Khadamat-Darmani)) : ");
        Insurance insurance = Insurance.TAMIN;
        Insurance[] i = Insurance.values();
        int choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < i.length) {
            insurance = i[choose];
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
        Doctor doctor = findDoctor(order.getToBeHospitalizedPart());
        if (doctor == null) {
            System.out.println("No Doctors Found !! ");
            return null;
        }
        Patient patient = new Patient(patientIDs++, order.getName(), nationalID, order.getIllnessType(),
                humanKind, age, doctor, order.getToBeHospitalizedPart(), insurance, order.getDate());
        Boolean addedToRoom = false;
        if (order.getToBeHospitalizedPart() == PartOfHospital.EMERGENCY) {
            addedToRoom = emergency.addPatient(patient, chooseRoom);
        } else {
            addedToRoom = ordinary.addPatient(patient, chooseRoom);
        }
        if (addedToRoom) {
            patients.add(patient);
            doctor.addPatient(patient);
            return patient;
        }
        return getPatient(nationalID);
    }
    public void removePatient(Patient patient) {
        patient.setDisCharged();
        System.out.print(patient.getBill());
        addToIncome(patient.getFinalCost());
        getDoctorWithID(patient.getDoctorID()).removePatient(patient);
        switch (patient.getPart()) {
            case ORDINARY:
                ordinary.removePatient(patient);
                break;
            case EMERGENCY:
                emergency.removePatient(patient);
                break;
            default:
                break;
        }
        System.out.print("\nPatient Successfully DisCharged\n");
    }
    public void printPartPatients() {
        System.out.print("Part (1(Ordinary), 2(Emergency)) : ");
        int i = scanner.nextInt();
        if (i == 1) {
            System.out.println("<< Ordinary >>");
            for (Patient p : ordinary.getPatients()) {
                System.out.println(p.getNationalID());
            }
        } else if (i == 2) {
            System.out.println("<< Emergency >>");
            for (Patient p : emergency.getPatients()) {
                System.out.println(p.getNationalID());
            }
        }
    }
    public void printPatientsOfDr() {
        System.out.print("Enter Doctor's Id :");
        Doctor dr = getDoctorWithID(scanner.nextInt());
        if (dr != null) {
            ArrayList<LocalDate> dates = getInterval();
            String patients = "";
            for (Patient p : dr.getAllPatients()) {
                if (p.getDisChargingDate() != null && p.getDisChargingDate().compareTo(dates.get(0)) < 0) {
                    continue;
                } else if (p.getHospitalizationDate().compareTo(dates.get(1)) > 0) {
                    continue;
                } else {
                    patients += p.getNationalID() + "  ";
                }
            }
            System.out.println("patients = " + patients);
        } else {
            System.out.println("No Such a Doctor exists...");
        }
    }
    public void printPatientsIntv() {
        ArrayList<LocalDate> dates = getInterval();
        for (Patient p : patients) {
            if (!p.isDisCharged()) {
                continue;
            }
            if (p.getHospitalizationDate().compareTo(dates.get(0)) >= 0 &&
                    p.getDisChargingDate().compareTo(dates.get(1)) <= 0) {
                System.out.println("N.ID : " + p.getNationalID() + "\nPart : " + p.getPart() + "\nRoom : " +
                        p.getRoomID() + "\nHospitalization Interval : " + p.getHospitalizationDate() + " -> " +
                        p.getDisChargingDate() + "\n");
            }
        }
    }
    public Doctor findDoctor(PartOfHospital p) {
        ArrayList<Doctor> doctors1 = null;
        if (p == PartOfHospital.ORDINARY) {
            doctors1 = ordinary.getDoctors();
        } else if (p == PartOfHospital.EMERGENCY) {
            doctors1 = emergency.getDoctors();
        }
        if (doctors1.size() > 0) {
            int min = doctors1.get(0).getFreeSpaceNumber();
            Doctor doctor = doctors1.get(0);
            for (Doctor d : doctors1) {
                if (d.getFreeSpaceNumber() > min) {
                    min = d.getFreeSpaceNumber();
                    doctor = d;
                }
            }
            if (doctor.getFreeSpaceNumber() > 0) {
                return doctor;
            }
        }
        return null;
    }
    public Patient getPatient(int nationalID) {
        for (Patient p : patients) {
            if (p.getNationalID() == nationalID && !p.isDisCharged()) {
                return p;
            }
        }
        return null;
    }
    public void addNewRoom() {
        System.out.print("Enter the Part you want to add Room (1(Ordinary), 2(Emergency) :");
        int partValue = scanner.nextInt();
        System.out.print("Enter number of Beds in the Room : ");
        int numberOfBeds = scanner.nextInt();
        if (partValue == 1) {
            ordinary.addRoom(numberOfBeds, firstBedCost);
        } else if (partValue == 2) {
            emergency.addRoom(numberOfBeds, firstBedCost);
        }
    }
    public Room getRoom() {
        Room room = null;
        System.out.print("Enter Part of Hospital (1(Ordinary), 2(Emergency)) : ");
        int partValue = scanner.nextInt();
        System.out.print("Enter Room ID for See : ");
        int id = scanner.nextInt();
        if (partValue == 1) {
            room = ordinary.getRoom(id);
        } else if (partValue == 2) {
            room = emergency.getRoom(id);
        } else {
            System.out.println("Wrong Input! Try Again");
            getRoom();
        }
        if (room == null) {
            System.out.println("No Such a Room Found!");
        }
        return room;
    }
    public ArrayList<Room> getOrdinaryRooms() {
        return ordinary.getRooms();
    }
    public ArrayList<Room> getEmergencyRooms() {
        return emergency.getRooms();
    }
    public void printUnAvailableRooms() {
        ordinary.printUnAvailableRooms();
        emergency.printUnAvailableRooms();
    }
    public void autoSet(int part) {
        ArrayList<Nurse> nurses1 = null;
        ArrayList<Doctor> doctors1 = null;
        if (part == 1) {
            nurses1 = ordinary.getNursesForShiftSet();
            doctors1 = ordinary.getDoctors();
        } else if (part == 2) {
            nurses1 = emergency.getNursesForShiftSet();
            doctors1 = emergency.getDoctors();
        }
        for (Doctor d : doctors1) {
            d.clearNurses();
        }
        for (Nurse n : nurses1) {
            n.clearDoctors();
        }
        if (nurses1.size() == 0 || doctors1.size() == 0) {
            return;
        } else {
            int doctorIndex = 0;
            for (int i = 0; i < nurses1.size(); i++) {
                if (nurses1.get(i).isDeleted()) {
                    continue;
                }
                for (int j = 0; j < 2; j++, doctorIndex++) {
                    if (i >= doctors1.size()) {
                        continue;
                    }
                    if (doctorIndex == doctors1.size()) {
                        doctorIndex = 0;
                    }
                    while (doctors1.get(doctorIndex).isDeleted()) {
                        doctorIndex++;
                        if (doctorIndex == doctors1.size()) {
                            break;
                        }
                    }
                    doctors1.get(doctorIndex).setNurse(nurses1.get(i));
                    nurses1.get(i).setDoctor(doctors1.get(doctorIndex));
                }
                nurses1.get(i).setShifts(scanner);
            }
        }
    }
    public ArrayList<LocalDate> getInterval() {
        ArrayList<LocalDate> dates = new ArrayList<>();
        LocalDate tmp = setDate();
        dates.add(LocalDate.of(tmp.getYear(), tmp.getMonth(), tmp.getDayOfMonth()));
        tmp = setDate();
        dates.add(LocalDate.of(tmp.getYear(), tmp.getMonth(), tmp.getDayOfMonth()));
        if (dates.get(0).compareTo(dates.get(1)) > 0) {
            System.out.println("Wrong Dates");
            getInterval();
        }
        return new ArrayList<>(dates);
    }
    public static LocalDate setDate() {
        System.out.print("Enter Date (1 for now, 2 for manual set) : ");
        int choose = scanner.nextInt();
        if (choose == 1) {
            return LocalDate.now();
        } else if (choose == 2) {
            System.out.print("Enter Date (Like this (2020 2 1)) : ");
            int year = scanner.nextInt();
            int month = scanner.nextInt();
            int day = scanner.nextInt();
            if (Main.checkDate(year, month, day)) {
                return LocalDate.of(year, month, day);
            } else {
                System.out.println("Wrong Arguments");
            }
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
        }
        return setDate();
    }
    public void addToIncome(double cost) {
        income += cost;
    }
    public void printIncomeInterval() {
        ArrayList<LocalDate> dates = getInterval();
        double sum = 0;
        for (Patient p : patients) {
            if (p.isDisCharged() && p.getDisChargingDate().compareTo(dates.get(0)) >= 0 &&
                    p.getDisChargingDate().compareTo(dates.get(1)) <= 0) {
                sum += p.getFinalCost();
            }
        }
        System.out.println("\n" + "Hospital Income : " + sum);
    }
    public void printShiftsOfPart(int partId) {
        if (partId == 1) {
            ordinary.printShifts();
        } else if (partId == 2) {
            emergency.printShifts();
        }
    }
}