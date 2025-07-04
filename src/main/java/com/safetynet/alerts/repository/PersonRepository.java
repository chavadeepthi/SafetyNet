package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonFileReadService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    private final JsonFileReadService jsonFileReadService;
    List<Person> personList;

    public PersonRepository(JsonFileReadService jsonFileReadService){
        this.jsonFileReadService = jsonFileReadService;

    }

    public List<Person> processJSONPerson(){
        if (personList == null) { // only load once
            JsonNode personNode = jsonFileReadService.getRootNode().path("persons");
            personList = jsonFileReadService.getObjectMapper().convertValue(
                    personNode,
                    new TypeReference<List<Person>>() {}
            );
        }
        return personList;
    }
    public List<String> processAllEmail(String city){
        if(personList==null)
            processJSONPerson();
        List<String> emails = new ArrayList<>();
        for (Person person : personList) {
            if (person.getCity() != null && person.getCity().equalsIgnoreCase(city)) {
                String email = person.getEmail();
                if (email != null && !email.trim().isEmpty() && !emails.contains(email)) {
                    emails.add(email);
                }
            }
        }

        return emails;
    }
    public Person findByFullName(String firstName, String lastName) {
        for (Person person : processJSONPerson()) {
            if (person.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                    person.getLastName().equalsIgnoreCase(lastName.trim())) {
                return person;
            }
        }
        return null;
    }



}
