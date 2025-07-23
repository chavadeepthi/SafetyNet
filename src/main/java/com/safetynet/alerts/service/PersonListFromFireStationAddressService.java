package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.view.PersonAndFireStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PersonListFromFireStationAddressService {
    private PersonAndFireStation personAndFireStation;
    private Person person;
    private List<FireStation> fireStations;
    private MedicalRecord medicalRecord;
    private FireStationRepository fireStationRepository;
    private PersonService personService;
    private MedicalRecordService medicalRecordService;

    public PersonListFromFireStationAddressService(FireStationRepository fireStationRepository,
                                                   PersonService personService,
                                                   MedicalRecordService medicalRecordService){
        this.fireStationRepository = fireStationRepository;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    public List<String> getFireStationAddress(String stationNumber){
        List<String> stationAddressList = new ArrayList<>();
        log.info("Station Number "+stationNumber);
        // Avoid null pointer if data not loaded yet
        if (fireStations == null) {
            fireStations = fireStationRepository.processJSONFireStation();
        }
        for (FireStation firestationId : fireStations) {
            if (firestationId.getStation().trim().equalsIgnoreCase(stationNumber.trim())) {
                stationAddressList.add(firestationId.getAddress());
            }
        }
        log.info("Station Address List "+stationAddressList);
        return stationAddressList;
    }
    public List<PersonAndFireStation> getPeopleListinAddress(String stationNumber){
        List<String> stationAddressList = getFireStationAddress(stationNumber);
        List<Person> peopleList = personService.findPersonsByAddresses(stationAddressList);

        List<PersonAndFireStation> peopleInFireStationLimit= new ArrayList<>();

        for (Person person : peopleList) {
            PersonAndFireStation paf = new PersonAndFireStation();
            paf.setFirstName(person.getFirstName());
            paf.setLastName(person.getLastName());
            paf.setAddress(person.getAddress());
            paf.setPhoneNumber(person.getPhone());

            //paf.setStationNumber(stationNumber);
            MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            if (record != null) {
                paf.setBirthData(record.getBirthdate());


            }
            String birthDateString = paf.getBirthData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            if (birthDateString != null && !birthDateString.isEmpty()) {
                LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(birthDate, currentDate).getYears();
                paf.setAge(age);
//                if (age > 18) {
//                    paf.setAge(age); // Assuming paf has a setAge(int) method
//                }
            }
            peopleInFireStationLimit.add(paf);
        }

        return peopleInFireStationLimit;
    }

    public List<PersonAndFireStation> getChildrenListinAddress(String address){

        List<Person> peopleList = personService.findChildByAddress(address);

        List<PersonAndFireStation> peopleInFireStationLimit= new ArrayList<>();

        for (Person person : peopleList) {
            PersonAndFireStation paf = new PersonAndFireStation();
            paf.setFirstName(person.getFirstName());
            paf.setLastName(person.getLastName());
            paf.setAddress(person.getAddress());
            paf.setPhoneNumber(person.getPhone());

            //paf.setStationNumber(stationNumber);
            MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());
            if (record != null) {
                paf.setBirthData(record.getBirthdate());


            }
            String birthDateString = paf.getBirthData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            if (birthDateString != null && !birthDateString.isEmpty()) {
                LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(birthDate, currentDate).getYears();
                paf.setAge(age);
                if (age < 18) {

                    peopleInFireStationLimit.add(paf);  }
            }
            //peopleInFireStationLimit.add(paf);
        }

        return peopleInFireStationLimit;
    }
}
