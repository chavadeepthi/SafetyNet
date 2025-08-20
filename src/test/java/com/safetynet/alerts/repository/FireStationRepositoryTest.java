package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FireStationRepositoryTest {
    @Mock
    private JsonFileReadRespository jsonFileReadRepositoryMock;

    @InjectMocks
    public FireStationRepository fireStationRepositoryMock;

    public ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        fireStationRepositoryMock = new FireStationRepository(jsonFileReadRepositoryMock, objectMapper);

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

    @Test
    void findStationAddressbyNumberListTest() throws Exception{
        setupMockJsonData();
        fireStationRepositoryMock.processJSONFireStation();

        List<String> result = fireStationRepositoryMock.findStationAddressbyNumberList(List.of("1","3"));
        assertNotNull(result);

    }

    @Test
    void findStationByAddressTest() throws Exception{
        setupMockJsonData();
        fireStationRepositoryMock.processJSONFireStation();

        String result = fireStationRepositoryMock.findStationByAddress("1509 Culver St");
        assertNotNull(result);
        assertEquals("3", result);

    }
    @Test
    void writeFireStationtoJSON_Success() throws Exception {
        // Arrange
        setupMockJsonData();

        // Create a new FireStation list with 2 entries
        List<FireStation> updatedList = List.of(
                new FireStation("1509 Culver St", "3"), // existing one
                new FireStation("29 15th St", "2")      // new one
        );

        // We want to capture what gets passed to writeToJsonFile
        ArgumentCaptor<JsonNode> captor = ArgumentCaptor.forClass(JsonNode.class);

        // Act
        fireStationRepositoryMock.writeFireStationtoJSON(updatedList);

        // Assert
        verify(jsonFileReadRepositoryMock, times(1)).writeToJsonFile(captor.capture());

        JsonNode writtenRoot = captor.getValue();

        // The root should contain 2 firestations now
        assertEquals(2, writtenRoot.get("firestations").size());
        assertEquals("1509 Culver St", writtenRoot.get("firestations").get(0).get("address").asText());
        assertEquals("29 15th St", writtenRoot.get("firestations").get(1).get("address").asText());
    }

}
