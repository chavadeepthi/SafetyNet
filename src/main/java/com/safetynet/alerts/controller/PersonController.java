package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personList;

    public PersonController(PersonService personList){
        this.personList = personList;
    }

    @GetMapping("/persons")
    public List<Person> getPersonList(){
        return personList.getAllPerson();
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailList(String city)
    {
        return personList.processAllEmail(city);
    }
    @PostMapping("/person")
    public List<Person> addPerson(@RequestBody Person personObject) {
        return personList.addPerson(personObject);
    }

    //Put
    @PutMapping("/person/{firstname}")
    public List<Person> updatePerson( @PathVariable String firstname, @RequestBody Person updatePerson)
    {
        log.info("Updating Person Details");
        return personList.updatePerson(updatePerson, firstname);
    }
    //
    // and Delete (First and Last Name)

}

