package com.safetynet.alerts.controller;


import com.safetynet.alerts.service.FirstResponderService;

import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/station/{stationNumber}")
    public AgeGroupingView getPersonList(@PathVariable String stationNumber){
        log.info("Retrieving All Person List from given Station number");
        return firstResponderService.getPeopleListinAddress(stationNumber);
    }
    @GetMapping("/childAlert/{address}")
    public List<FirstResponderView> getChildrenList(@PathVariable String address){
        log.info("Retrieving All Children List");
        return firstResponderService.getChildrenListinAddress(address);
    }
}
