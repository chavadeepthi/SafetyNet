package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderAddressView;
import com.safetynet.alerts.view.FirstResponderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FirstResponderService {
    private FirstResponderView firstResponderView;
    private Person person;
    private List<FireStation> fireStations;
    private MedicalRecord medicalRecord;
    //private AgeGroupingView ageGroupingView;

    private FireStationService fireStationService;
    private PersonService personService;
    private MedicalRecordService medicalRecordService;

    @Autowired
    public FirstResponderService(

            FireStationService fireStationService,
            PersonService personService,
            MedicalRecordService medicalRecordService){

                this.personService = personService;
                this.medicalRecordService = medicalRecordService;
                this.fireStationService = fireStationService;
    }

    public List<String> getFireStationAddress(String stationNumber){
        List<String> stationAddressList = new ArrayList<>();
        //log.info("In Station Number "+stationNumber);
        // Avoid null pointer if data not loaded yet
        if (fireStations == null) {
            fireStations = fireStationService.getAllFireStation();
        }
        for (FireStation firestationId : fireStations) {
            if (firestationId.getStation().trim().equalsIgnoreCase(stationNumber.trim())) {
                stationAddressList.add(firestationId.getAddress());
            }
        }
        //log.info("Station Address List "+stationAddressList);
        //log.info("In Station Number "+stationAddressList);
        return stationAddressList;
    }
    public AgeGroupingView getPeopleListinAddress(String stationNumber){
        List<String> stationAddressList = getFireStationAddress(stationNumber);
        log.info("Address List "+stationAddressList);

        List<Person> peopleList = personService.findPersonsByAddresses(stationAddressList);

        List<FirstResponderView>  peopleInFireStationLimit = new ArrayList<>();
        AgeGroupingView ageGroupingList= new AgeGroupingView();
        int adultCount = 0;
        int childCount = 0;

        for (Person person : peopleList) {
            FirstResponderView paf = new FirstResponderView();
            paf.setFirstName(person.getFirstName());
            paf.setLastName(person.getLastName());
            paf.setAddress(person.getAddress());
            paf.setPhoneNumber(person.getPhone());
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
                if (age > 18) {
                    adultCount++;
                } else {
                    childCount++;
                }
            }

            peopleInFireStationLimit.add(paf);
        }
        ageGroupingList.setAdultCount(adultCount);
        ageGroupingList.setChildCount(childCount);
        ageGroupingList.setPersonList(peopleInFireStationLimit);

        log.info(String.valueOf(ageGroupingList));

        return ageGroupingList;

    }

    public List<FirstResponderView> getChildrenListinAddress(String address){

        List<Person> peopleList = personService.findChildByAddress(address);
        List<FirstResponderView> childList= new ArrayList<>();
        List<FirstResponderView> adultList= new ArrayList<>();
        boolean hasChild = false;

        for (Person person : peopleList) {
            FirstResponderView paf = new FirstResponderView();
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
                    hasChild= true;
                    childList.add(paf);

                }
            else {
                adultList.add(paf);

            }
            }

            //peopleInFireStationLimit.add(paf);
        }
        if(hasChild)
        {
            childList.addAll(adultList);
        }

        return childList;

    }

    public List<FirstResponderAddressView> getPeopleMedicalHistroy(String address){

        List<Person> peopleList = personService.findChildByAddress(address);
        List<FirstResponderAddressView> peopleMedicationList= new ArrayList<>();
        String stationNumber = fireStationService.findStationNumberbyAddress(address);
        for (Person person : peopleList) {
            FirstResponderAddressView paf = new FirstResponderAddressView();
            paf.setFirstName(person.getFirstName());
            paf.setLastName(person.getLastName());
            paf.setAddress(person.getAddress());
            paf.setPhoneNumber(person.getPhone());
            paf.setStationNumber(stationNumber);

            //paf.setStationNumber(stationNumber);
            MedicalRecord record = medicalRecordService.getMedicalRecordByFullName(person.getFirstName(), person.getLastName());

            if (record != null) {
                paf.setBirthData(record.getBirthdate());
                paf.setAllergyList(record.getAllergies());
                paf.setMedicationList(record.getMedications());
            }
            String birthDateString = paf.getBirthData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            if (birthDateString != null && !birthDateString.isEmpty()) {
                LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(birthDate, currentDate).getYears();
                paf.setAge(age);
            }
            peopleMedicationList.add(paf);
        }

        return peopleMedicationList;


    }


}
