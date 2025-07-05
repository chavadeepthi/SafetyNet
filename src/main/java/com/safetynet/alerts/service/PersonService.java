package com.safetynet.alerts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {


    //private final JsonFileReadService jsonFileReadService;
    //private List<Person> personList;
    PersonRepository personRepository;


    @Autowired //Optional for single constructor
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson() {
        return personRepository.processJSONPerson();
    }

    public List<String> processAllEmail(String city) {

     return personRepository.processAllEmail(city);
    }
    public List<Person> addOrUpdatePerson(Person personObject) {
        List<Person> personList = getAllPerson();
        Person existingPerson = personRepository.findByFullName(personObject.getFirstName(), personObject.getLastName());

        if (existingPerson == null) {
            getAllPerson().add(personObject);
        } else {
            existingPerson.setAddress(personObject.getAddress());
            existingPerson.setCity(personObject.getCity());
            existingPerson.setZip(personObject.getZip());
            existingPerson.setPhone(personObject.getPhone());
            existingPerson.setEmail(personObject.getEmail());
        }

        return personList;
    }

}
