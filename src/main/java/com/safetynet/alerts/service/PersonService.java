package com.safetynet.alerts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {


    //private final JsonFileReadService jsonFileReadService;
    private List<Person> personList;
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

}
