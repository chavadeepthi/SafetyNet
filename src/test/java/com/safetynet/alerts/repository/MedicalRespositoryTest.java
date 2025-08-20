package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MedicalRespositoryTest {
    @Mock
    JsonFileReadRespository jsonFileReadRespositoryMock;

    @InjectMocks
    MedicalRecordRepository medicalRecordRepositoryMock;

    public ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        medicalRecordRepositoryMock = new MedicalRecordRepository(jsonFileReadRespositoryMock, objectMapper);
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
    @Test
    void writeMedicalRecordtoJSON_Success() throws Exception {
        // Arrange
        setupMockJsonData();

        List<MedicalRecord> updatedList = List.of(
                new MedicalRecord(
                        "Smart",
                        "Pants",
                        "03/06/1984",
                        List.of("aznol:350mg", "hydrapermazol:100mg"),
                        List.of("nillacilan")
                ),
        new MedicalRecord(
                "John",
                "Boyd",
                "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan"))
        );// new one


        // We want to capture what gets passed to writeToJsonFile
        ArgumentCaptor<JsonNode> captor = ArgumentCaptor.forClass(JsonNode.class);

        // Act
        medicalRecordRepositoryMock.writeMedicalRecordtoJSON(updatedList);

        // Assert
        verify(jsonFileReadRespositoryMock, times(1)).writeToJsonFile(captor.capture());

        JsonNode writtenRoot = captor.getValue();

        // The root should contain 2 firestations now
        assertEquals(2, writtenRoot.get("medicalrecords").size());
        assertEquals("Pants", writtenRoot.get("medicalrecords").get(0).get("lastName").asText());
        assertEquals("Boyd", writtenRoot.get("medicalrecords").get(1).get("lastName").asText());
    }
}
