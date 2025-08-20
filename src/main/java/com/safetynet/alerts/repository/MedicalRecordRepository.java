package com.safetynet.alerts.repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.safetynet.alerts.model.MedicalRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class MedicalRecordRepository {
    private final JsonFileReadRespository jsonFileReadRespository;
    private final ObjectMapper objectMapper;
    List<MedicalRecord> medicalRecords;

    public MedicalRecordRepository(JsonFileReadRespository jsonFileReadRespository, ObjectMapper objectMapper){
        this.jsonFileReadRespository = jsonFileReadRespository;
        this.objectMapper = objectMapper;
    }
    public List<MedicalRecord> processJSONMedicalRecord() {
        if (medicalRecords == null) { // only load once
            JsonNode medicalRecordNode = jsonFileReadRespository.getRootNode().path("medicalrecords");
            medicalRecords = jsonFileReadRespository.getObjectMapper().convertValue(
                    medicalRecordNode,
                    new TypeReference<List<MedicalRecord>>() {}
            );
        }
        return medicalRecords;
    }

    public void writeMedicalRecordtoJSON(List<MedicalRecord> medicalRecordList) {

        try {
            // Convert updated Medical Record list back into a JsonNode
            ObjectNode root = (ObjectNode) jsonFileReadRespository.getRootNode();  // root of data.json
            ArrayNode medicalRecordNode = objectMapper.valueToTree(medicalRecordList);

            // Replace the Medical Record array in the root
            root.set("medicalrecords", medicalRecordNode);

            // Reuse your existing method
            jsonFileReadRespository.writeToJsonFile(root);

        } catch (Exception e) {
            log.error("Error persisting Medical Record updates: {}", e.getMessage(), e);
            throw new RuntimeException("Error persisting Medical Record updates", e);
        }

    }


}
