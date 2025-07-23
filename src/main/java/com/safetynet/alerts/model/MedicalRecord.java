package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MedicalRecord {
    String firstName;
    String lastName;
    String birthdate;
    List<String> medications;
    List<String> allergies;
}
