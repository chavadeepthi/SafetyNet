package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStation {
    private String address;
    private String station;

    public String getStation(){
        return station;
    }

    public void setStation(String station){
        this.station = station;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }


//    public FireStation(String address, String station) {
//        this.address = address;
//        this.station = station;
//    }

}
