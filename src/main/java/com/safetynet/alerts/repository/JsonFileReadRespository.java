package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
            log.error(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }


    }

    public JsonNode getRootNode() {
        return rootNode;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Write back to the JSON file
     * @param updatedNode new JsonNode to persist
     */
    public void writeToJsonFile(JsonNode updatedNode) {
        File jsonFile = new File("src/main/resources/data.json");
        try {
            if (jsonFile == null) {
                log.error("JSON file reference is null, cannot write back!");
                return;
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, updatedNode);
            this.rootNode = updatedNode; // update local reference too
            log.info("Successfully wrote updates to {}", jsonFile.getAbsolutePath());

        } catch (IOException e) {
            log.error("Error writing to JSON file: {}", e.getMessage(), e);
        }
    }
}
