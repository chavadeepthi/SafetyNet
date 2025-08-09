package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FirstResponderService;

import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderAddressView;
import com.safetynet.alerts.view.FirstResponderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FirstResponderController {
    FirstResponderService firstResponderService;
    //List<String> addressList = new ArrayList<>();

    @Autowired
    public FirstResponderController(FirstResponderService firstResponderService){

        this.firstResponderService = firstResponderService;
    }

    @GetMapping("/station")
    public ResponseEntity<AgeGroupingView> getPersonList(@RequestParam String stationNumber){
        log.info("Retrieving All Person List from given Station number");
        AgeGroupingView result = firstResponderService.getPeopleListinAddress(stationNumber);

        if (result.getPersonList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Total number of Record "+ result.getPersonList().size());
            return ResponseEntity.ok(result);
        }
    }
    /** http://localhost:8080/childAlert?address=<address> **/

    @GetMapping("/childAlert")
    public ResponseEntity<List<FirstResponderView>> getChildrenList(@RequestParam String address){
        log.info("Retrieving All Children List");
         List<FirstResponderView> result =  firstResponderService.getChildrenListinAddress(address);
        log.info("Total number of Record "+ result.size());
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Total number of Record "+ result.size());
            return ResponseEntity.ok(result);
        }
    }

    /** http://localhost:8080/phoneAlert?firestation=<firestation_number>
     This URL must return a list of phone numbers of residents served by the fire station. It
     will be used to send emergency text messages to specific households. **/

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneALerts(@RequestParam String firestation){
        log.info("Retrieving All Person Phone numbers from given Station number" +firestation);
        AgeGroupingView result = firstResponderService.getPeopleListinAddress(firestation);
        log.info("Total number of Record "+ result.getPersonList().size());

        List<String> phoneNumbers = result.getPersonList().stream()
                .map(FirstResponderView::getPhoneNumber)
                .collect(Collectors.toList());

        if(phoneNumbers.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
            return ResponseEntity.ok(phoneNumbers);
        }
    }
/** Controller for http://localhost:8080/fire?address=<address> **/
    @GetMapping("/fire")
    public ResponseEntity<List<FirstResponderAddressView>> getPeopleMedicalHistroy(@RequestParam String address){
        log.info("Retrieving All Person List with medical record results for address "+address);
        List<FirstResponderAddressView> result =  firstResponderService.getPeopleMedicalHistroy(address);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            log.info("Total number of Record "+ result.size());
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<List<FirstResponderAddressView>> getFloodApiDetails(
            @RequestParam List<String> stationNumbers) {
        log.info("Retrieving People info for stations: " + stationNumbers);
        List<FirstResponderAddressView> result = firstResponderService.getFloodApiDetails(stationNumbers);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/personInfolastName")
    public ResponseEntity<List<FirstResponderAddressView>> getPersonInfoByLastName(@RequestParam String lastName)
    {
        log.info("Retrieving People info with Last Name: " + lastName);
        List<FirstResponderAddressView> result = firstResponderService.getPersonInfoWithLastName(lastName);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
