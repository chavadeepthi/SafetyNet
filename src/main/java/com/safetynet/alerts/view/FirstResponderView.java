package com.safetynet.alerts.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstResponderView {
    String firstName;
    String lastName;
    String address;
    //String stationNumber;
    int age;
    String birthData;
    String phoneNumber;


}
