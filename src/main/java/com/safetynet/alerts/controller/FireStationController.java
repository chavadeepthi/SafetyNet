package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.model.FireStation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FireStationController {

    private final FireStationService fileService;
    //private List<SafetynetFireStation> matchingFileService;

    public FireStationController(FireStationService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/firestationList")
    public List<FireStation> getFireStation(){
        return fileService.processJSONFireStation();
    }

    @GetMapping("/firestation")
    public List<FireStation> getFirestationByNumber(@RequestParam String stationNumber) {
        return fileService.findByStationNumber(stationNumber);
    }
}
