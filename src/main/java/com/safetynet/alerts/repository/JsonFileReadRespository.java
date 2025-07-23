package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class JsonFileReadRespository {

    private final ObjectMapper objectMapper;
    private JsonNode rootNode;

    @Autowired
    public JsonFileReadRespository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        JsonFileParse();
    }

    public void JsonFileParse() {

        try {
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();
            this.rootNode = objectMapper.readTree(inputStream);


        } catch (IOException e) {
            log.error("Error reading the JSON file: " + e.getMessage());
            //System.err.println("Error reading the JSON file: " + e.getMessage());
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
