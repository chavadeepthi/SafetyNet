package com.safetynet.alerts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SafetyNetPersonController {

    private final SafetynetPersonService personList;

    public SafetyNetPersonController(SafetynetPersonService personList){
        this.personList = personList;
    }

    @GetMapping("/personList")
    public List<SafetynetPerson> getPersonList(){
        return personList.processJSONPerson();
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailList(String city){
        return personList.processAllEmail(city);
    }

}

