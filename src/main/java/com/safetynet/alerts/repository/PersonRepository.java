package com.safetynet.alerts.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PersonRepository {
    private final JsonFileReadRespository jsonFileReadRespository;
    List<Person> personList;
    ObjectMapper objectMapper;

    public PersonRepository(JsonFileReadRespository jsonFileReadRespository, ObjectMapper objectMapper){
        this.jsonFileReadRespository = jsonFileReadRespository;
        this.objectMapper = objectMapper;

    }

    public List<Person> processJSONPerson(){
        if (personList == null) { // only load once
            JsonNode personNode = jsonFileReadRespository.getRootNode().path("persons");
            personList = jsonFileReadRespository.getObjectMapper().convertValue(
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
        if(personList==null)
            processJSONPerson();
        for (Person person : personList) {
            if (person.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                    person.getLastName().equalsIgnoreCase(lastName.trim())) {
                return person;
            }
        }
        return null;
    }

    public List<Person> findAddressByLastName(String lastName) {
        if (personList == null) {
            processJSONPerson();
        }

        List<Person> personByLastNameList = new ArrayList<>();
        //String trimmedLastName = lastName.replaceAll("^\"|\"$", "");

        for (Person person : personList) {

            if (person.getLastName().trim().equalsIgnoreCase(lastName.trim())) {
                personByLastNameList.add(person);
            }
        }

       return personByLastNameList;
    }
    public void writePersontoJSON(List<Person> peopleList) {

        try {
            // Convert updated Medical Record list back into a JsonNode
            ObjectNode root = (ObjectNode) jsonFileReadRespository.getRootNode();  // root of data.json
            ArrayNode peopleListNode = objectMapper.valueToTree(peopleList);

            // Replace the Medical Record array in the root
            root.set("persons", peopleListNode);

            // Reuse your existing method
            jsonFileReadRespository.writeToJsonFile(root);

        } catch (Exception e) {
            log.error("Error persisting Person List updates: {}", e.getMessage(), e);
            throw new RuntimeException("Error persisting Person updates", e);
        }
    }





    }
