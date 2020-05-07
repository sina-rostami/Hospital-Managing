package ir.ac.kntu;

import java.time.LocalDate;

public class HospitalizationOrder {

    private String name;
    private PartOfHospital toBeHospitalizedPart;
    private IllnessType illnessType;
    private LocalDate setDate;

    public HospitalizationOrder(String name, PartOfHospital toBeHospitalizedPart,
                                IllnessType illnessType, LocalDate localDate) {
        this.name = name;
        this.toBeHospitalizedPart = toBeHospitalizedPart;
        this.illnessType = illnessType;
        this.setDate = localDate;
    }

    public String getName() {
        return name;
    }

    public PartOfHospital getToBeHospitalizedPart() {
        return toBeHospitalizedPart;
    }

    public IllnessType getIllnessType() {
        return illnessType;
    }

    public LocalDate getDate() {
        return setDate;
    }

    public String toString() {
        return "name : " + name + "\n" +
                "to be Hospitalized Part : " + toBeHospitalizedPart + "\n" +
                "Illness Type : " + illnessType + "\n" +
                "set Date : " + setDate + "\n";
    }
}


