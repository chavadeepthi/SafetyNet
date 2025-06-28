package com.safetynet.alerts;

import java.util.List;

// Need to create Wrapper function ad Json file id Starting with { instead of [ so I have to use class
public class SafetynerFireStationWrapper {

    private List<SafetynetFireStation> firestation;

    public List<SafetynetFireStation> getFirestation (){
        return firestation;
    }
    public void setFirestations(List<SafetynetFireStation> firestation) {
        this.firestation = firestation;
    }

}
