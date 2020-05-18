package ir.ac.kntu;

import java.util.*;

public class Part {
    private String name;
    private int roomIds = 100;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Nurse> nurses = new ArrayList<>();
    private int id;

    public Part(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean addPatient(Patient patient, ChooseRoom chooseRoom) {
        Room room = getRoomForHospitalization(chooseRoom);
        if (room == null) {
            System.out.println("No Rooms Found");
            return false;
        }
        room.addPatient(patient);
        patient.setRoom(room);
        patients.add(patient);
        return true;
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
        getRoom(patient).removePatient(patient);
    }

    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public ArrayList<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public void addNurse(Nurse nurse) {
        nurses.add(nurse);
    }

    public void removeNurse(Nurse nurse) {
        nurses.remove(nurse);
    }

    public ArrayList<Nurse> getNursesForShiftSet() {
        return new ArrayList<>(nurses);
    }

    public int getId() {
        return id;
    }

    public void addRoom(int numberOfBeds, int firstBedCost, Scanner scanner) {
        System.out.print("Have Refrigerator (0 , 1) ? ");
        int[] equips = new int[3];
        equips[0] = scanner.nextInt();
        System.out.print("Have TV (0 , 1) ? ");
        equips[1] = scanner.nextInt();
        System.out.print("Have A/C (0 , 1) ? ");
        equips[2] = scanner.nextInt();
        ArrayList<RoomEquipment> equipments = new ArrayList<>();
        if(equips[0] == 1) {
            equipments.add(new RoomEquipment(001));
        }
        if(equips[1] == 1) {
            equipments.add(new RoomEquipment(002));
        }
        if(equips[2] == 1) {
            equipments.add(new RoomEquipment(003));
        }
        rooms.add(new Room(roomIds, numberOfBeds, true, firstBedCost, equipments));
        System.out.println("Room Successfully Added with ID : " + roomIds++);
    }

    public Room getRoom(int id) {
        for (Room r : rooms) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public Room getRoom(Patient patient) {
        for (Room r : rooms) {
            if (r.getPatients().contains(patient)) {
                return r;
            }
        }
        return null;
    }

    public Room getRoomForHospitalization(ChooseRoom chooseRoom) {
        if (rooms.size() > 0) {
            Room room = rooms.get(0);
            switch (chooseRoom) {
                case MID_FULL:
                    for (int i = 1; i < rooms.size(); i++) {
                        if (rooms.get(i).getFreeSpacesNum() < room.getFreeSpacesNum() &&
                                rooms.get(i).isAvailable()) {
                            room = rooms.get(i);
                        }
                    }
                    break;
                case EMPTY:
                    for (int i = 1; i < rooms.size(); i++) {
                        if (rooms.get(i).getFreeSpacesNum() > room.getFreeSpacesNum() &&
                                rooms.get(i).isAvailable()) {
                            room = rooms.get(i);
                        }
                    }
                    break;
                default:
                    break;
            }
            if (room.getFreeSpacesNum() != 0 && room.isAvailable()) {
                return room;
            } else if (!room.isAvailable()) {
                System.out.println("Room is unAvailable !");
            } else {
                System.out.println("No Free Space in Room !");
            }
        }
        return null;
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public void printUnAvailableRooms() {
        System.out.println(name);
        for (Room r : rooms) {
            if (!r.isAvailable()) {
                System.out.print(r.getId() + "  ");
            }
        }
        System.out.println();
    }

    public void printShifts() {
        ArrayList<Shift> shiftsD = new ArrayList<>();
        ArrayList<Shift> shiftsN = new ArrayList<>();
        System.out.println("Doctors : ");
        for (Doctor d : doctors) {
            if (!d.isDeleted()) {
                for (Shift shift : d.getShifts()) {
                    if (!hasThisShift(shift, shiftsD)) {
                        shiftsD.add(shift);
                    }
                }
            }
        }
        for (Shift s : shiftsD) {
            System.out.print(s.toString() + "  ");
        }
        System.out.println();
        System.out.println("Nurses : ");
        for (Nurse n : nurses) {
            if (!n.isDeleted()) {
                for (Shift shift : n.getShifts()) {
                    if (!hasThisShift(shift, shiftsN)) {
                        shiftsN.add(shift);
                    }
                }
            }
        }
        for (Shift s : shiftsN) {
            System.out.print(s.toString() + "  ");
        }
        System.out.println();
    }

    public boolean hasThisShift(Shift shift, ArrayList<Shift> shifts) {
        for (int i = 0; i < shifts.size(); i++) {
            if (shifts.get(i).getDay() == shift.getDay() && shifts.get(i).getShift() == shift.getShift()) {
                return true;
            }
        }
        return false;
    }
}