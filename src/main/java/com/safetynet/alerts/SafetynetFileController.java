package com.safetynet.alerts;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SafetynetFileController {

    private final SafetynetFileService fileService;
    //private List<SafetynetFireStation> matchingFileService;

    public SafetynetFileController(SafetynetFileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/firestationList")
    public List<SafetynetFireStation> getFireStation(){
        return fileService.processJSONFireStation();
    }

    @GetMapping("/firestation")
    public List<SafetynetFireStation> getFirestationByNumber(@RequestParam String stationNumber) {
        return fileService.findByStationNumber(stationNumber);
    }
}
