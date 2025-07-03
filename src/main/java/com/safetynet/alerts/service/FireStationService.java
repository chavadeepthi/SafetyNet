package com.safetynet.alerts.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private final JsonFileReadService jsonFileReadService;
    private List<FireStation> fireStation;


   public FireStationService(JsonFileReadService jsonFileReadService){
        this.jsonFileReadService = jsonFileReadService;
    }

    public List<FireStation> processJSONFireStation() {
        if (fireStation == null) { // only load once
            JsonNode fireStationNode = jsonFileReadService.getRootNode().path("firestations");
            fireStation = jsonFileReadService.getObjectMapper().convertValue(
                    fireStationNode,
                    new TypeReference<List<FireStation>>() {}
            );
        }
        return fireStation;
    }

    public List<FireStation> findByStationNumber (String StationNumber)
    {
        List<FireStation> matchingStation = new ArrayList<>();
        // Avoid null pointer if data not loaded yet
        if (fireStation == null) {
            processJSONFireStation();
        }
        for (FireStation firestationId : fireStation) {
            if (firestationId.getStation().trim().equalsIgnoreCase(StationNumber.trim())) {
                matchingStation.add(firestationId);
            }
        }
        return matchingStation;

    }

}
