package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class JsonFileReadService {

    private final ObjectMapper objectMapper;
    private JsonNode rootNode;

    @Autowired
    public JsonFileReadService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        JsonFileParse();
    }

    public void JsonFileParse() {

        try {
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();
            this.rootNode = objectMapper.readTree(inputStream);


        } catch (IOException e) {
            System.err.println("Error reading the JSON file: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public JsonNode getRootNode() {
        return rootNode;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
