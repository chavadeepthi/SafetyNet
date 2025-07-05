package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.JsonFileReadService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FireStationRepository {

    //List<FireStation> fireStations = new ArrayList<FireStation>();


    private final JsonFileReadService jsonFileReadService;
    List<FireStation> fireStations;


    public FireStationRepository(JsonFileReadService jsonFileReadService){
        this.jsonFileReadService = jsonFileReadService;
    }

    public List<FireStation> processJSONFireStation() {
        if (fireStations == null) { // only load once
            JsonNode fireStationNode = jsonFileReadService.getRootNode().path("firestations");
            fireStations = jsonFileReadService.getObjectMapper().convertValue(
                    fireStationNode,
                    new TypeReference<List<FireStation>>() {}
            );
        }
        return fireStations;
    }


    public List<FireStation> findByStationId (String StationNumber)
    {
        List<FireStation> matchingStation = new ArrayList<>();
        // Avoid null pointer if data not loaded yet
        if (fireStations == null) {
            processJSONFireStation();
        }
        for (FireStation firestationId : fireStations) {
            if (firestationId.getStation().trim().equalsIgnoreCase(StationNumber.trim())) {
                matchingStation.add(firestationId);
            }
        }
        return matchingStation;

    }

}
