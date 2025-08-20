package com.safetynet.alerts.service;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FireStationService {


    private FireStationRepository fireStationRepository;
    public List<FireStation> fireStationList = new ArrayList<>();

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
    public String findStationNumberbyAddress (String StationNumber)
    {

        return fireStationRepository.findStationByAddress(StationNumber);


    }
    public List<String> findStationAddressbyNumberList (List<String> stationNumbers)
    {
            return fireStationRepository.findStationAddressbyNumberList(stationNumbers);
    }
    public List<FireStation> addNewFireStation(FireStation fireStationObject){
        fireStationList.add(fireStationObject);
        fireStationRepository.writeFireStationtoJSON(fireStationList);
        return fireStationList;
    }

    /**
     * Update FireStaiton will take Station Address as input and update Statiotn ID
     *
     * @param updatedStation Station RequestBody
     * @param address Input Address to update Station ID
     * @return return List of Firestations for Validation
     */
    public boolean updateFireStation(FireStation updatedStation, String address)
    {

        boolean found = false;

        for (FireStation fs : fireStationList) {
            if (fs.getAddress().equalsIgnoreCase(address)) {
                fs.setStation(updatedStation.getStation());
                found = true;

                break;
            }

        }
        fireStationRepository.writeFireStationtoJSON(fireStationList);

        return found;
    }

    public boolean deleteByAddress(String address) {

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
        fireStationRepository.writeFireStationtoJSON(fireStationList);

        return removed;
    }
}
