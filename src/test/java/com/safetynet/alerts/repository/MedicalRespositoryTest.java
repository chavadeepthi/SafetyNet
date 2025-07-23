package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MedicalRespositoryTest {
    @Mock
    JsonFileReadRespository jsonFileReadRespositoryMock;

    @InjectMocks
    MedicalRecordRepository medicalRecordRepositoryMock;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    private void setupMockJsonData() throws Exception {
        String json = """
                {
                    "medicalrecords": [
                        { "firstName":"John", "lastName":"Boyd", 
                        "birthdate":"03/06/1984", 
                        "medications":["aznol:350mg", "hydrapermazol:100mg"], 
                        "allergies":["nillacilan"] }
                
                    ]
                }
                """;
        JsonNode rootNode = objectMapper.readTree(json);
        when(jsonFileReadRespositoryMock.getRootNode()).thenReturn(rootNode);
        System.out.println(rootNode.toPrettyString());
        when(jsonFileReadRespositoryMock.getObjectMapper()).thenReturn(objectMapper);

        medicalRecordRepositoryMock.processJSONMedicalRecord(); // preload data
    }

    @Test
    void testProcessAllMedical_Success () throws Exception {
        setupMockJsonData();

        List<MedicalRecord> result = medicalRecordRepositoryMock.processJSONMedicalRecord();

        assertEquals(1, result.size());
        assertEquals("03/06/1984", result.get(0).getBirthdate());
        assertEquals(2, result.get(0).getMedications().size());


    }
}
