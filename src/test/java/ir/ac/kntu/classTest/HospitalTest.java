package ir.ac.kntu.classTest;

import org.junit.Test;
import ir.ac.kntu.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class HospitalTest {
    private Hospital hospital = new Hospital("hospital", "address", 1000
            , new Scanner(System.in));
    @Test
    public void getDoctorWithID() {
        ArrayList<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift(1,1));
        shifts.add(new Shift(1,2));
        shifts.add(new Shift(1,3));
        Doctor doctor = new Doctor("Doci",111,shifts, 1);
        hospital.addDoctor(doctor);
        assertEquals(hospital.getDoctorWithID(111), doctor);
    }

    @Test
    public void getNurseWithID() {
        ArrayList<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift(1,1));
        shifts.add(new Shift(1,2));
        shifts.add(new Shift(1,3));
        Nurse nurse = new Nurse("Nurse", 1112, 1);
    }

    @Test
    public void addAndGetNewOrder() {
    }

    @Test
    public void addAndGetPatient() {
    }

    @Test
    public void getInterval() {
    }
}