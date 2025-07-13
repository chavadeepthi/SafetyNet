package com.safetynet.alerts.repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.JsonFileReadService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    private final JsonFileReadService jsonFileReadService;
    List<MedicalRecord> medicalRecords;

    public MedicalRecordRepository(JsonFileReadService jsonFileReadService){
        this.jsonFileReadService = jsonFileReadService;
    }
    public List<MedicalRecord> processJSONMedicalRecord() {
        if (medicalRecords == null) { // only load once
            JsonNode medicalRecordNode = jsonFileReadService.getRootNode().path("medicalrecords");
            medicalRecords = jsonFileReadService.getObjectMapper().convertValue(
                    medicalRecordNode,
                    new TypeReference<List<MedicalRecord>>() {}
            );
        }
        return medicalRecords;
    }


}
