package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    String firstName; String lastName;
    String birthdate;
    List<String> medications;
    List<String> allergies;
}
