package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.PersonListFromFireStationAddressService;

import com.safetynet.alerts.view.PersonAndFireStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PersonListFromFireStationAddressController {
    PersonListFromFireStationAddressService personListFromFireStationAddressService;
    //List<String> addressList = new ArrayList<>();

    public PersonListFromFireStationAddressController(PersonListFromFireStationAddressService personListFromFireStationAddressService){

        this.personListFromFireStationAddressService = personListFromFireStationAddressService;
    }

    @GetMapping("/station/{stationNumber}")
    public List<PersonAndFireStation> getPersonList(@PathVariable String stationNumber){
        log.info("Retrieving All Person List");
        return personListFromFireStationAddressService.getPeopleListinAddress(stationNumber);
    }
    @GetMapping("/childAlert/{address}")
    public List<PersonAndFireStation> getChildrenList(@PathVariable String address){
        log.info("Retrieving All Children List");
        return personListFromFireStationAddressService.getChildrenListinAddress(address);
    }
}
