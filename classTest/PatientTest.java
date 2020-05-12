package ir.ac.kntu;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PatientTest {



    @Test
    public void getDoctorID() {
        ArrayList<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift(1,1));
        shifts.add(new Shift(1,2));
        shifts.add(new Shift(1,3));
        Doctor doctor = new Doctor("Doci",111,shifts, 1);
         Patient patient = new Patient(1,"p",11,IllnessType.OTHER,HumanKind.MALE,
                21,doctor,PartOfHospital.ORDINARY,Insurance.KHADAMAT,null);
         assertEquals(111, patient.getDoctorID());
    }

    @Test
    public void getNationalID() {
        Patient patient = new Patient(1,"p",11,IllnessType.OTHER,HumanKind.MALE,
                21,null,PartOfHospital.ORDINARY,Insurance.KHADAMAT,null);
        assertEquals(11, patient.getNationalID());
    }

    @Test
    public void getPart() {
        Patient patient = new Patient(1,"p",11,IllnessType.OTHER,HumanKind.MALE,
                21,null,PartOfHospital.ORDINARY,Insurance.KHADAMAT,null);
        assertEquals(PartOfHospital.ORDINARY, patient.getPart());
    }

    @Test
    public void isDisCharged() {
        Patient patient = new Patient(1,"p",11,IllnessType.OTHER,HumanKind.MALE,
                21,null,PartOfHospital.ORDINARY,Insurance.KHADAMAT,null);
        assertFalse(patient.isDisCharged());
    }
}