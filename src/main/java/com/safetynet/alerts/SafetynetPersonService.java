package com.safetynet.alerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SafetynetPersonService {

    private final ObjectMapper objectMapper;
    private List<SafetynetPerson> personList;

    @Autowired
    public SafetynetPersonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<SafetynetPerson> processJSONPerson()
    {
        try{

            InputStream inputStream = new ClassPathResource("data.json").getInputStream();
            JsonNode rootNode = objectMapper.readTree(inputStream);

            JsonNode personNode = rootNode.get("persons");
            personList = objectMapper.convertValue(personNode,
                    new TypeReference<List<SafetynetPerson>>() {
            });

            return personList;

        }
        catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
            e.printStackTrace();
    }
        return null;
    }

    public List<String> processAllEmail(String city){

        if (personList == null)
        {
            processJSONPerson();
        }
        List<String> emails = new ArrayList<>();
        for (SafetynetPerson person : personList) {
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
