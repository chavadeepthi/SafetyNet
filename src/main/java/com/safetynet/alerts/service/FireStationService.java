package com.safetynet.alerts.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FireStationService {


    private FireStationRepository fireStationRepository;
    private List<FireStation> fireStationList = new ArrayList<>();

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }
    @PostConstruct
    public void init() {
        fireStationList = fireStationRepository.processJSONFireStation();
    }

    public List<FireStation> getAllFireStation() {
        return fireStationList;

    }

    public List<FireStation> findByStationNumber (String StationNumber)
    {

        return fireStationRepository.findByStationId(StationNumber);


    }
    public List<FireStation> addNewFireStation(FireStation fireStationObject){
        //List<FireStation> fireStation = fireStationRepository.processJSONFireStation();
        FireStation existingItem = fireStationRepository.findByStationAddress(fireStationObject.getAddress());
        if(existingItem == null){

            fireStationList.add(fireStationObject);
        }
        else{
            existingItem.setAddress(fireStationObject.getAddress());
            existingItem.setStation(fireStationObject.getStation());
        }
        return fireStationList;
    }

    public List<FireStation> updateFireStation(FireStation updatedStation, String address)
    {
        //List<FireStation> fireStationList = fireStationRepository.processJSONFireStation();
        for (FireStation fs : fireStationList) {
            if (fs.getAddress().equalsIgnoreCase(address)) {
                fs.setStation(updatedStation.getStation());
                fs.setAddress(updatedStation.getAddress());

            }
        }
        return fireStationList;
    }
    public List<FireStation> deleteByAddress(String address) {

        Iterator<FireStation> iterator = fireStationList.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            FireStation fs = iterator.next();
            if (fs.getAddress().equalsIgnoreCase(address)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        // Optionally persist the updated list here if needed
        return fireStationList;
    }
}
