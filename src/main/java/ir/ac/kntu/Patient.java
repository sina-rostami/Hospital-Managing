package ir.ac.kntu;

import java.time.LocalDate;
import java.util.*;

public class Patient {
    private int id;
    private String name;
    private int nationalID;
    private int age;
    private Doctor doctor;
    private LocalDate hospitalizationDate;
    private LocalDate disChargingDate = null;
    private IllnessType illnessType;
    private HumanKind humanKind;
    private PartOfHospital partOfHospital;
    private Room room;
    private Insurance insurance;
    private Boolean isDisCharged;
    private ChooseRoom chooseRoom;

    public Patient(int id, String name, int nationalID, IllnessType illnessType, HumanKind humanKind, int age,
                   Doctor doctor, PartOfHospital partOfHospital, Insurance insurance, LocalDate hospitalizationDate,
                   ChooseRoom chooseRoom) {
        this.id = id;
        this.name = name;
        this.nationalID = nationalID;
        this.illnessType = illnessType;
        this.humanKind = humanKind;
        this.age = age;
        this.doctor = doctor;
        this.partOfHospital = partOfHospital;
        this.insurance = insurance;
        this.isDisCharged = false;
        this.hospitalizationDate = hospitalizationDate;
        this.chooseRoom = chooseRoom;
    }

    public ChooseRoom getChooseRoom() {
        return chooseRoom;
    }


    public void setRoom(Room room) {
        this.room = room;
    }

    public int getRoomID() {
        return room.getId();
    }

    public void editPatient(Scanner scanner) {
        System.out.print("Enter Name : ");
        this.name = scanner.next();
        System.out.print("Enter National ID : ");
        this.nationalID = scanner.nextInt();
        System.out.print("Enter Age : ");
        this.age = scanner.nextInt();
        System.out.print("Enter Illness Type (1(Burn), 2(Impact), 3(Accident), 4(Other)) : ");
        IllnessType[] i = IllnessType.values();
        int choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < i.length) {
            this.illnessType = i[choose];
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            editPatient(scanner);
        }
        System.out.print("Enter Human Kind (1(Male), 2(Female)) : ");
        HumanKind[] h = HumanKind.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < h.length) {
            this.humanKind = h[choose];
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            editPatient(scanner);
        }
        System.out.print("Enter Insurance Type (1(Tamin-Ejtemaei), 2(Niro-haye-Mosallah), 3(Khadamat-Darmani)) : ");
        Insurance[] insurances = Insurance.values();
        choose = scanner.nextInt() - 1;
        if (choose >= 0 && choose < i.length) {
            this.insurance = insurances[choose];
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            editPatient(scanner);
        }
        System.out.print("Enter Date of Hospitalization (1 for now, 2 for manual set) : ");
        choose = scanner.nextInt();
        if (choose == 1) {
            this.hospitalizationDate = LocalDate.now();
        } else if (choose == 2) {
            this.hospitalizationDate = setDate(scanner);
            if (hospitalizationDate == null) {
                Main.clearScreen();
                System.out.println("Wrong Input! Try Again...");
                editPatient(scanner);
            }
        } else {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            editPatient(scanner);
        }
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public int getDoctorID() {
        if(doctor != null) {
            return doctor.getId();
        }
        return 0;
    }

    public int getNationalID() {
        return nationalID;
    }

    public PartOfHospital getPart() {
        return partOfHospital;
    }

    public Boolean isDisCharged() {
        return isDisCharged;
    }

    public String nurses() {
        String nurses = "";
        if (doctor != null) {
            for (Nurse n : doctor.getNurses()) {
                nurses += n.getId() + "   ";
            }
        }
        return nurses;
    }

    public String getInfo() {
        return "\n" + "ID : " + id + "\n" +
                "Name : " + name + "\n" +
                "Kind of Humanity : " + humanKind + "\n" +
                "National Id :" + nationalID + "\n" +
                "Age : " + age + "\n" +
                "Insurance Type : " + insurance + "\n" +
                "Hospitalization Date : " + hospitalizationDate + "\n" +
                "Hospitalized Part : " + partOfHospital + "\n" +
                "Room ID : " + room.getId() + "\n" +
                "Illness Type : " + illnessType + "\n" +
                "Doctor ID : " + getDoctorID() + "\n" +
                "Nurses IDs : " + nurses() + "\n" +
                "DisCharging Date : " + disChargingDate + "\n";

    }

    public void setDisCharged() {
        LocalDate localDate = Hospital.setDate();
        if (localDate == null) {
            Main.clearScreen();
            System.out.println("Wrong Input! Try Again...");
            this.setDisCharged();
        }
        while (localDate.compareTo(this.hospitalizationDate) < 0) {
            localDate = Hospital.setDate();
        }
        disChargingDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
        isDisCharged = true;
    }

    public LocalDate getHospitalizationDate() {
        return hospitalizationDate;
    }

    public LocalDate getDisChargingDate() {
        return disChargingDate;
    }

    public LocalDate setDate(Scanner scanner) {
        System.out.print("Enter Date (Like this (2020 2 1)) : ");
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        if (Main.checkDate(year, month, day)) {
            return LocalDate.of(year, month, day);
        } else {
            System.out.println("Wrong Arguments");
        }
        return null;
    }

    public String getBill() {
        return getInfo() +
                "Hospitalized Days : " + getHospitalizedDays() + "\n" +
                "One night Cost : " + room.getRoomCost() + "\n" +
                "Total Price : " + getTotalCost() + "\n" +
                "Final Price : " + getFinalCost() + "\n";
    }

    public int getHospitalizedDays() {
        return disChargingDate.compareTo(hospitalizationDate);
    }

    public double getTotalCost() {
        return getHospitalizedDays() * room.getRoomCost();
    }

    public double getFinalCost() {
        double totalCost = getTotalCost();
        double finalCost = totalCost;
        switch (insurance) {
            case TAMIN:
                finalCost = 0.9 * totalCost;
                break;
            case MOSALAH:
                finalCost = 0.5 * totalCost;
                break;
            case KHADAMAT:
                finalCost = 0.75 * totalCost;
                break;
            default:
                break;
        }
        return finalCost;
    }
}
