package com.safetynet.alerts.controller;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.model.FireStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class FireStationController {

    //private static final Logger log = LoggerFactory.getLogger(FireStationController.class);
    private final FireStationService fireStationService;
    //private List<SafetynetFireStation> matchingFileService;

    public FireStationController(FireStationService fireStationService){

        this.fireStationService = fireStationService;
    }

    @GetMapping("/firestations")
    public List<FireStation> getFireStations(){
        log.info("Getting All Fire Stations");
        List<FireStation> fireStationsResults = fireStationService.getAllFireStation();
        log.info("Found Fire Stations of Length "+fireStationsResults.size());
        return fireStationsResults;
    }

    @GetMapping("/firestation/{stationNumber}")
    public List<FireStation> getFirestationByNumber(@PathVariable String stationNumber) {
        log.info("Searching for fire stations witn Station ID ", stationNumber);
        return fireStationService.findByStationNumber(stationNumber);
    }
    @PostMapping("/firestation")
    public List<FireStation> addNewFireStation(@RequestBody FireStation fireStationObject) {
        log.info("Adding new FireStation record ", fireStationObject);
        return fireStationService.addNewFireStation(fireStationObject);
    }

    // Put Fire Stations address

    @PutMapping("/firestation/{address}")
    public ResponseEntity<String> UpdateFirestationByAddress( @PathVariable String address, @RequestBody FireStation updatedStation){
        log.info("Updating FireStation with Address ", address);
        int updated = fireStationService.updateFireStation(updatedStation, address);

        if (updated == 1) {
            return ResponseEntity.ok("FireStation updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found: " + address);
        }
    }
    // Delete with Station Number(grouping) and Address
    @DeleteMapping("/firestation/{address}")
    public ResponseEntity<String> deleteFireStation(@PathVariable String address) {
        log.info("Deleting Firstation with Address ", address);
        boolean response =  fireStationService.deleteByAddress(address);
        if (response) {
            return ResponseEntity.ok("FireStation Deletion successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found: " + address);
        }

    }
}
