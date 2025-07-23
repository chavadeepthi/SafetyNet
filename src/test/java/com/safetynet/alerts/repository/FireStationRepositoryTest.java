package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class FireStationRepositoryTest {
    @Mock
    private JsonFileReadRespository jsonFileReadRepositoryMock;

    @InjectMocks
    private FireStationRepository fireStationRepositoryMock;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    private void setupMockJsonData() throws Exception {
        String json = """
                {
                    "firestations": [
                        {
                            "address": "1509 Culver St",
                            "station": "3"
                        }
                    ]
                }
                """;
        JsonNode rootNode = objectMapper.readTree(json);
        when(jsonFileReadRepositoryMock.getRootNode()).thenReturn(rootNode);
        System.out.println(rootNode.toPrettyString());
        when(jsonFileReadRepositoryMock.getObjectMapper()).thenReturn(objectMapper);

        fireStationRepositoryMock.processJSONFireStation(); // preload data
    }

    @Test
    void testProcessAllFireStation_Success () throws Exception {
        setupMockJsonData();

        List<FireStation> result = fireStationRepositoryMock.processJSONFireStation();

        assertEquals(1, result.size());
        assertEquals("1509 Culver St", result.get(0).getAddress());
        assertEquals("3", result.get(0).getStation());


    }
    @Test
    void findByStationIDTest_Success()  throws Exception{
        // Same setup as previous test
        setupMockJsonData();
        // Loading List
        fireStationRepositoryMock.processJSONFireStation();
        // Act
        List<FireStation> station = fireStationRepositoryMock.findByStationId("3");

        // Assert
        assertNotNull(station);
        assertEquals("3", station.get(0).getStation());
    }
    @Test
    void findByStationAddressTest_Success()  throws Exception{
        // Same setup as previous test
        setupMockJsonData();
        // Loading List
        fireStationRepositoryMock.processJSONFireStation();
        // Act
        FireStation station = fireStationRepositoryMock.findByStationAddress("1509 Culver St");

        // Assert
        assertNotNull(station);
        assertEquals("1509 Culver St", station.getAddress());
    }
}
