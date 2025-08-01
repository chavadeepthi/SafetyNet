package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FirstResponderService;

import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
