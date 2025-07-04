package com.safetynet.alerts.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

   //private List<FireStation> fireStation;
    private FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public List<FireStation> getAllFireStation() {
        return fireStationRepository.processJSONFireStation();

    }

    public List<FireStation> findByStationNumber (String StationNumber)
    {

        return fireStationRepository.findByStationId(StationNumber);


    }
    public List<FireStation> addOrUpdateFireStation(FireStation fireStationObject){
        List<FireStation> fireStation = fireStationRepository.processJSONFireStation();
        FireStation existingItem = fireStationRepository.findByStationAddress(fireStationObject.getAddress());
        if(existingItem == null){

            fireStation.add(fireStationObject);
        }
        else{
            existingItem.setAddress(fireStationObject.getAddress());
            existingItem.setStation(fireStationObject.getStation());
        }
        return fireStation;
    }

}
