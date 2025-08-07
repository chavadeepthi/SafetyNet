package com.safetynet.alerts.view;

import com.safetynet.alerts.model.MedicalRecord;
import lombok.Data;

import java.util.List;

@Data
public class FirstResponderAddressView {
    String firstName;
    String lastName;
    String address;
    String stationNumber;
    int age;
    String birthData;
    String phoneNumber;
    List<String> medicationList;
    List<String> allergyList;
}
