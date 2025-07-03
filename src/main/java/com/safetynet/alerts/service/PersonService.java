package com.safetynet.alerts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {


    private final JsonFileReadService jsonFileReadService;
    private List<Person> personList;

    @Autowired //Optional for single constructor
    public PersonService(JsonFileReadService jsonFileReadService) {
        this.jsonFileReadService = jsonFileReadService;
    }

    public List<Person> processJSONPerson() {
        if (personList == null) { // only load once
            JsonNode personNode = jsonFileReadService.getRootNode().path("persons");
            personList = jsonFileReadService.getObjectMapper().convertValue(
                    personNode,
                    new TypeReference<List<Person>>() {}
            );
        }
        return personList;
    }

    public List<String> processAllEmail(String city) {

        if (personList == null) {
            processJSONPerson();
        }
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

}
