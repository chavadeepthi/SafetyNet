package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.model.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personList;

    public PersonController(PersonService personList){
        this.personList = personList;
    }

    @GetMapping("/persons")
    public List<Person> getPersonList(){
        return personList.getAllPerson();
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailList(String city){
        return personList.processAllEmail(city);
    }
    @PostMapping("/person")
    public List<Person> addOrUpdateFireStation(@RequestBody Person personObject) {
        return personList.addOrUpdatePerson(personObject);
    }

    //Put and Delete (First and Last Name)

}

