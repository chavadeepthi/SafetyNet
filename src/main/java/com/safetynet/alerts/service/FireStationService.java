package com.safetynet.alerts.service;
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
    public String findStationNumberbyAddress (String StationNumber)
    {

        return fireStationRepository.findStationByAddress(StationNumber);


    }
    public List<FireStation> addNewFireStation(FireStation fireStationObject){
        fireStationList.add(fireStationObject);
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
        boolean response = false;
        //List<FireStation> fireStationList = fireStationRepository.processJSONFireStation();
        for (FireStation fs : fireStationList) {
            if (fs.getAddress().equalsIgnoreCase(address)) {
                fs.setStation(updatedStation.getStation());
                response = true;
                //fs.setAddress(updatedStation.getAddress());

            }
        }
        return response;
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

        // Optionally persist the updated list here if needed
        return removed;
    }
}
