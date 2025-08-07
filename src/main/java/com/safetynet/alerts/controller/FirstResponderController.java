package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FirstResponderService;

import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderAddressView;
import com.safetynet.alerts.view.FirstResponderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AgeGroupingView getPersonList(@RequestParam String stationNumber){
        log.info("Retrieving All Person List from given Station number");
        AgeGroupingView result = firstResponderService.getPeopleListinAddress(stationNumber);
        log.info("Total number of Record "+ result.getPersonList().size());
        return result;
    }
    @GetMapping("/childAlert")
    public List<FirstResponderView> getChildrenList(@RequestParam String address){
        log.info("Retrieving All Children List");
         List<FirstResponderView> result =  firstResponderService.getChildrenListinAddress(address);
        log.info("Total number of Record "+ result.size());
        return result;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneALerts(@RequestParam String firestation){
        log.info("Retrieving All Person Phone numbers from given Station number" +firestation);
        AgeGroupingView result = firstResponderService.getPeopleListinAddress(firestation);
        log.info("Total number of Record "+ result.getPersonList().size());

        List<String> phoneNumbers = result.getPersonList().stream()
                .map(FirstResponderView::getPhoneNumber)
                .collect(Collectors.toList());

        return phoneNumbers;
    }

    @GetMapping("/fire")
    public List<FirstResponderAddressView> getPeopleMedicalHistroy(@RequestParam String address){
        log.info("Retrieving All Person List with medical record results for address "+address);
        List<FirstResponderAddressView> result =  firstResponderService.getPeopleMedicalHistroy(address);
        log.info("Total number of Record "+ result.size());
        return result;
    }
}
