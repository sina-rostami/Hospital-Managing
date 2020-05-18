package ir.ac.kntu;
import java.time.LocalDate;
import java.util.*;
public class Hospital {
    private String name;
    private String address;
    private int firstBedCost;
    private Part ordinary = new Part(1, "Ordinary");
    private Part emergency = new Part(2, "Emergency");
    private ArrayList<Part> parts;
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private ArrayList<HospitalizationOrder> orders = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<SecurityMan> securityMEN = new ArrayList<>();
    private ArrayList<FacilityMan> facilityMEN  = new ArrayList<>();
    private int patientIDs = 11111;
    private int doctorIDs = 111;
    private int nurseIDs = 111;
    private int secManIDs = 111;
    private int facManIDs = 111;
    private double income = 0;
    private static Scanner scanner;
    private ObjectMaker objectMaker;
    public Hospital(String name, String address, int firstBedCost, Scanner scanner) {
        this.name = name;
        this.address = address;
        this.firstBedCost = firstBedCost;
        this.scanner = scanner;
        parts = new ArrayList<>(Arrays.asList(new Part(1, "Ordinary"), new Part(2, "Emergency"),
                new Part(3, "Burn"), new Part(4, "ICU")));
        objectMaker = new ObjectMaker();
        objectMaker.setRequirements(scanner, parts, this);
    }
    public void addDoctor(Doctor doctor) {
        if(doctor != null) {
            doctors.add(doctor);
        }
    }
    public void addOrEditDoctor(int id) {
        Doctor doctor = objectMaker.newDoctor(id, doctorIDs);
        if(doctor != null) {
            doctors.add(doctor);
            parts.get(doctor.getPartId() - 1).addDoctor(doctor);
            if(id == 0) {
                doctorIDs++;
            }
            System.out.println("Doctor Added Successfully with ID : " + doctor.getId() + "\n");
            autoSet(doctor.getPartId());
        }
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
        doctors.remove(d);
        d.clearNurses();
        parts.get(d.getPartId() - 1).removeDoctor(d);
        autoSet(d.getPartId());
        System.out.println("Doctor with ID : " + d.getId() + " Successfully Deleted!\n");
    }
    public void seeDoctor() {
        System.out.print("Enter Doctor's ID : ");
        int id = scanner.nextInt();
        Doctor doctor = getDoctorWithID(id);
        if (doctor != null) {
            System.out.println(doctor.getInfo());
            System.out.print("Enter 1(edit) , 2(remove) or 3(back) : ");
            int choose1 = scanner.nextInt();
            if (choose1 == 1) {
                addOrEditDoctor(id);
            } else if (choose1 == 2) {
                deleteDoctor(doctor);
            }
        } else {
            System.out.println("No Such a Doctor Found :(\n");
        }
    }
    public void printAllDoctors() {
        Main.clearScreen();
        for(Part p : parts) {
            System.out.println(p.getName());
            for (Doctor d : p.getDoctors()) {
                if (!d.isDeleted()) {
                    System.out.println(d.getInfo() + "\n");
                }
            }
        }
    }
    public ArrayList<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }
    public Nurse addNurse(int id) {
        Nurse n = objectMaker.newNurse(id, nurseIDs);
        nurses.add(n);
        parts.get(n.getPartId() - 1).addNurse(n);
        if(id == 0) {
            nurseIDs++;
        }
        System.out.println("Nurse Added Successfully with ID : " + n.getId() + "\n");
        autoSet(n.getPartId());
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
        nurses.remove(n);
        parts.get(n.getPartId() - 1).removeNurse(n);
        autoSet(n.getPartId());
        System.out.println("Nurse with ID : " + n.getId() + " Successfully Deleted!\n");
    }
    public void seeNurse() {
        System.out.print("Enter Nurse's ID : ");
        int id = scanner.nextInt();
        Nurse nurse = getNurseWithID(id);
        if (nurse != null) {
            System.out.println(nurse.getInfo());
            System.out.print("Enter 1(edit) , 2(remove) or 3(back) : ");
            int choose1 = scanner.nextInt();
            if (choose1 == 1) {
                nurse.editNurse(scanner);
            } else if (choose1 == 2) {
                deleteNurse(nurse);
            }
        } else {
            System.out.println("No Such a Nurse  Found :(\n");
        }
    }
    public void printAllNurses() {
        Main.clearScreen();
        for(Part p : parts) {
            System.out.println(p.getName());
            for (Nurse n : p.getNursesForShiftSet()) {
                if (!n.isDeleted()) {
                    System.out.println(n.getInfo() + "\n");
                }
            }
        }
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
    public Patient addAndGetPatient() {
        Patient patient = objectMaker.newPatient(patientIDs++);
        if(patient != null) {
            boolean addedToRoom = parts.get(patient.getPart().ordinal()).addPatient(patient,
                    patient.getChooseRoom());
            if (addedToRoom) {
                patients.add(patient);
                patient.getDoctor().addPatient(patient);
                return patient;
            } else {
                removePatient(patient);
            }
        }
        return patient;
    }
    public void removePatient(Patient patient) {
        patient.setDisCharged();
        System.out.print(patient.getBill());
        addToIncome(patient.getFinalCost());
        getDoctorWithID(patient.getDoctorID()).removePatient(patient);
        parts.get(patient.getPart().ordinal()).removePatient(patient);
        System.out.print("\nPatient Successfully DisCharged\n");
    }
    public void printPartPatients() {
        System.out.print("Part (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) : ");
        int i = scanner.nextInt();
        if(i <= parts.size()) {
            System.out.println(parts.get(i - 1).getName());
            for (Patient p : parts.get(i - 1).getPatients()) {
                System.out.println(p.getNationalID());
            }
        } else {
            System.out.println("Wrong Input !");
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
        ArrayList<Doctor> doctors1 = parts.get(p.ordinal()).getDoctors();
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
        System.out.print("Enter the Part you want to add Room (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) :");
        int partValue = scanner.nextInt();
        int numberOfBeds = 1;
        if(partValue != 4) {
            System.out.print("Enter number of Beds in the Room : ");
            numberOfBeds = scanner.nextInt();
        }

        if(partValue <= parts.size()) {
            parts.get(partValue - 1).addRoom(numberOfBeds, firstBedCost, scanner);
        }
    }
    public Room getRoom() {
        Room room = null;
        System.out.print("Enter Part of Hospital (1-Ordinary, 2-Emergency, 3-Burn, 4-ICU) : ");
        int partValue = scanner.nextInt();
        System.out.print("Enter Room ID for See : ");
        int id = scanner.nextInt();
        if (partValue <= parts.size()) {
            room = parts.get(partValue - 1).getRoom(id);
        } else {
            System.out.println("Wrong Input! Try Again");
            getRoom();
        }
        return room;
    }
    public void damageReport(Room room, int index) {
        index--;
        LocalDate localDate = LocalDate.now();
        int day = (localDate.getDayOfWeek().getValue() + 2) % 7;
        if(facilityMEN != null && facilityMEN.size() > 0 && index < room.getRoomEquipments().size() && index > 0 ) {
            FacilityMan facilityMan = facilityMEN.get(0);
            int min = facilityMan.getCloserShift(day);
            for (FacilityMan f : facilityMEN) {
                if(!f.isBusy() && f.getCloserShift(day) < min) {
                    min = f.getCloserShift(day);
                    facilityMan = f;
                }
            }
            room.getRoomEquipments().get(index).setOk(false);
            facilityMan.setBusy(true);
            System.out.println("Report Sent to Facility Man ID " + facilityMan.getId() + "!");
        } else {
            System.out.println("NO Facility man or No Equips!");
        }
    }
    public void printAllRooms() {
        for(Part p : parts) {
            System.out.println(p.getName());
            for(Room r : p.getRooms()) {
                System.out.println(r.getRoomInfo());
            }
        }
    }
    public void printUnAvailableRooms() {
        for (Part part: parts) {
            System.out.println(part.getName());
            part.printUnAvailableRooms();
        }
    }
    public void addSecMan(int id) {
        if(id != 0) {
            deleteSecMan(getSecMan(id));
        } else {
            id = secManIDs++;
        }
        SecurityMan securityMan = objectMaker.newSecMan(id);
        securityMEN.add(securityMan);
    }
    public SecurityMan getSecMan(int id) {
        for(SecurityMan man : securityMEN) {
            if(man.getId() == id) {
                return man;
            }
        }
        return null;
    }
    public void deleteSecMan(SecurityMan securityMan) {
        securityMEN.remove(securityMan);
        System.out.println("Sec man Successfully Removed!");
    }
    public void seeSecMan() {
        System.out.print("Enter ID : ");
        int id = scanner.nextInt();
        SecurityMan man = getSecMan(id);
        if(man != null) {
            System.out.println(man.toString() + "\nEnter 1(edit) , 2(remove) or 3(back) : ");
            int choose = scanner.nextInt();
            if (choose == 1) {
                addSecMan(man.getId());
            } else if (choose == 2) {
                deleteSecMan(man);
            }
        } else {
            System.out.println("Not Found !");
        }
    }
    public void printAllSecMen() {
        for(SecurityMan man : securityMEN) {
            System.out.println(man.toString());
        }
    }
    public void addFacMan(int id) {
        if(id != 0) {
            deleteFacMan(getFacMan(id));
        } else {
            id = facManIDs++;
        }
        FacilityMan facilityMan = objectMaker.newFacMan(id);
        facilityMEN.add(facilityMan);
    }
    public FacilityMan getFacMan(int id) {
        for(FacilityMan man : facilityMEN) {
            if(man.getId() == id) {
                return man;
            }
        }
        return null;
    }
    public void printAllFacMan() {
        for(FacilityMan man : facilityMEN) {
            System.out.println(man.toString());
        }
    }
    public void deleteFacMan(FacilityMan facilityMan) {
        facilityMEN.remove(facilityMan);
        System.out.println("Fac man Successfully Removed!");
    }
    public void autoSet(int part) {
        ArrayList<Nurse> nurses1 = parts.get(part - 1).getNursesForShiftSet();
        ArrayList<Doctor> doctors1 = parts.get(part - 1).getDoctors();
        for (Doctor d : doctors1) {
            d.clearNurses();
        }
        for (Nurse n : nurses1) {
            n.clearDoctors();
        }
        if (nurses1.size() == 0 ) {
            return;
        } else if (doctors1.size() == 0) {
            for(Nurse n : nurses1) {
                n.setShifts(scanner);
            }
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
        parts.get(partId).printShifts();
    }
}