package com.safetynet.alerts.repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    private final JsonFileReadRespository jsonFileReadRespository;
    List<MedicalRecord> medicalRecords;

    public MedicalRecordRepository(JsonFileReadRespository jsonFileReadRespository){
        this.jsonFileReadRespository = jsonFileReadRespository;
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


}
