package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.model.FireStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FireStationRepository {

    //List<FireStation> fireStations = new ArrayList<FireStation>();


    private final JsonFileReadRespository jsonFileReadRespository;
    private final ObjectMapper objectMapper;
    List<FireStation> fireStations;


    public FireStationRepository(JsonFileReadRespository jsonFileReadRespository, ObjectMapper objectMapper){
        this.jsonFileReadRespository = jsonFileReadRespository;
        this.objectMapper = objectMapper;
    }

    public List<FireStation> processJSONFireStation() {
        if (fireStations == null) { // only load once
            JsonNode fireStationNode = jsonFileReadRespository.getRootNode().path("firestations");
            fireStations = jsonFileReadRespository.getObjectMapper().convertValue(
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
    public FireStation findByStationAddress (String StationAddress)
    {

        if (fireStations == null) {
            processJSONFireStation();
        }
        for (FireStation firestationId : fireStations) {
            if (firestationId.getAddress().trim().equalsIgnoreCase(StationAddress.trim())) {
                return firestationId;
            }
        }
        return null;

    }

    public String findStationByAddress(String StationAddress){
        if (fireStations == null) {
            processJSONFireStation();
        }
        for (FireStation firestationId : fireStations) {
            if (firestationId.getAddress().trim().equalsIgnoreCase(StationAddress.trim())) {
                return firestationId.getStation();
            }
        }
        return null;
    }

    public List<String> findStationAddressbyNumberList (List<String> stationNumbers) {
        if (fireStations == null) {
            processJSONFireStation();
        }

        List<String> addresses = new ArrayList<>();
        for (String number : stationNumbers) {
            for (FireStation fs : fireStations) {
                if (fs.getStation().trim().equalsIgnoreCase(number.trim())) {
                    addresses.add(fs.getAddress());

                }
            }
        }
        return addresses;
    }

    public void writeFireStationtoJSON(List<FireStation> fireStationList) {

        try {
            // Convert updated fireStations list back into a JsonNode
            ObjectNode root = (ObjectNode) jsonFileReadRespository.getRootNode();  // root of data.json
            ArrayNode firestationsNode = objectMapper.valueToTree(fireStationList);

            // Replace the firestations array in the root
            root.set("firestations", firestationsNode);

            // Reuse your existing method
            jsonFileReadRespository.writeToJsonFile(root);

        } catch (Exception e) {
            log.error("Error persisting FireStation updates: {}", e.getMessage(), e);
            throw new RuntimeException("Error persisting FireStation updates", e);
        }

    }




}
