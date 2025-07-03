package com.safetynet.alerts.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private final ObjectMapper objectMapper;
    private List<FireStation> fireStation;

    @Autowired
    public FireStationService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public List<FireStation> processJSONFireStation(){
        try {
            //ObjectMapper objectMapper =new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();

            // Parse the full JSON
            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode fireStationNode = rootNode.get("firestations");
            // Deserialize that into a List<SafetynetFireStation>
            fireStation = objectMapper.convertValue(
                    fireStationNode,
                    new TypeReference<List<FireStation>>() {});

            //System.out.println(fireStation);
            return fireStation ;

        }catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

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
