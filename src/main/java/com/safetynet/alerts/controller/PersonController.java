package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PersonController {

    //private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personList;

    public PersonController(PersonService personList){
        this.personList = personList;
    }

    @GetMapping("/persons")
    public List<Person> getPersonList(){
        log.info("Retrieving All Person List");
        return personList.getAllPerson();
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailList(String city)
    {
        log.info("Get list of all people email from City", city);
        return personList.processAllEmail(city);
    }
    @PostMapping("/person")
    public List<Person> addPerson(@RequestBody Person personObject) {
        log.info("Adding new person record");
        return personList.addPerson(personObject);
    }

    //Put
    @PutMapping("/person")
    public ResponseEntity<String> updatePerson(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestBody Person updatePerson)
    {
        log.info("Updating Person Details");
        boolean update_person =  personList.updatePerson(updatePerson, firstName, lastName);
        if(update_person){
            return ResponseEntity.ok("Person Udpare successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person details not found: " + firstName + lastName);
        }

    }
    //
    // and Delete (First Name)
    // Delete with Station Number(grouping) and Address
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestParam String firstName,
                                               @RequestParam String lastName) {
        log.info("Deleting Person with first name ", firstName, lastName);
        boolean delete_person =  personList.deleteByFirstname(firstName, lastName);
        if(delete_person){
            return ResponseEntity.ok("Person Deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person details not found: " + firstName + lastName);
        }


    }

}

