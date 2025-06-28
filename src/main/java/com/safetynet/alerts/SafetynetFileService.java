package com.safetynet.alerts;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SafetynetFileService {

    private final ObjectMapper objectMapper;
    private List<SafetynetFireStation> fireStation;

    @Autowired
    public SafetynetFileService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public List<SafetynetFireStation> processJSONFireStation(){
        try {
            //ObjectMapper objectMapper =new ObjectMapper();
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();


            //As JSON file is starting with { had to user Wrapper function and call .class
            SafetynerFireStationWrapper fireStationWrapper = objectMapper.readValue(inputStream,SafetynerFireStationWrapper.class);

             fireStation = fireStationWrapper.getFirestation();

            //System.out.println(fireStation);
            return fireStation ;

        }catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    public List<SafetynetFireStation> findByStationNumber (String StationNumber)
    {
        List<SafetynetFireStation> matchingStation = new ArrayList<>();
        // Avoid null pointer if data not loaded yet
        if (fireStation == null) {
            processJSONFireStation();
        }
        for (SafetynetFireStation firestationId : fireStation) {
            if (firestationId.getStation().trim().equalsIgnoreCase(StationNumber.trim())) {
                matchingStation.add(firestationId);
            }
        }
        return matchingStation;

    }

}
