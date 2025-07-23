package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class FireStationControllerTest {
    @Mock
    FireStationService fireStationServiceMock;

    @InjectMocks
    FireStationController fireStationControllerMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFireStationTest(){
        // Arrange
        FireStation station1 = new FireStation("1509 Culver St", "23");
        FireStation station2 = new FireStation("1509 Culver St", "13");
        List<FireStation> mockItems = Arrays.asList(station1, station2);

        when(fireStationServiceMock.getAllFireStation()).thenReturn(mockItems);

        List<FireStation> result = fireStationControllerMock.getFireStations();

        assertEquals(2, result.size());
        assertEquals("1509 Culver St", result.get(0).getAddress());

    }
    @Test
    void updateFireStationSuccessTest(){
        //Arrange
        FireStation fs1 = new FireStation("123 Main St", "23");

        when(fireStationServiceMock.updateFireStation(fs1,"123 Main St")).thenReturn(true);

        ResponseEntity<String> result =
                fireStationControllerMock.UpdateFirestationByAddress
                        ("123 Main St", fs1);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("successfully"));


    }
    @Test
    void updateFireStationFail(){
        //Arrange

        FireStation fs1 = new FireStation("123 Main St", "23");

        when(fireStationServiceMock.updateFireStation(fs1,"123 Main St")).thenReturn(false);

        ResponseEntity<String> result =
                fireStationControllerMock.UpdateFirestationByAddress
                        ("123 Main St", fs1);
        assertEquals(404, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("not found"));


    }

    @Test
    void deleteFireStationSuccessTest(){
        //Arrange

        FireStation fs1 = new FireStation("123 Main St", "23");

        when(fireStationServiceMock.deleteByAddress("123 Main St")).thenReturn(true);

        ResponseEntity<String> result =
                fireStationControllerMock.deleteFireStation("123 Main St");

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("successfully"));


    }
    @Test
    void deleteFireStationFailTest(){
        //Arrange

        FireStation fs1 = new FireStation("123 Main St", "23");

        when(fireStationServiceMock.deleteByAddress("123 Main St")).thenReturn(false);

        ResponseEntity<String> result =
                fireStationControllerMock.deleteFireStation("123 Main St");


        assertEquals(404, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("not found"));


    }
}
