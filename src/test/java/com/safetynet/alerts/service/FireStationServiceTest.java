package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepositoryMock;

    @InjectMocks
    private FireStationService fireStationServiceMock;

    @Test
    public void getAllFireStationMock() {
        // Arrange
        FireStation station1 = new FireStation("1509 Culver St", "23");
        FireStation station2 = new FireStation("1509 Culver St", "13");
        List<FireStation> mockItems = Arrays.asList(station1, station2);

        when(fireStationRepositoryMock.processJSONFireStation()).thenReturn(mockItems);
        fireStationServiceMock.init();

        // Act
        List<FireStation> result = fireStationServiceMock.getAllFireStation();

        // Assert
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    public void findFireStationByNumberMock() {
        // Arrange

        String stationNumber = "23";
        FireStation fs1 = new FireStation("123 Main St", "23");
        FireStation fs2 = new FireStation("456 Elm St", "2");

        List<FireStation> mockItems = List.of(fs1);

        when(fireStationRepositoryMock.findByStationId(stationNumber)).thenReturn(mockItems);
        fireStationServiceMock.init();

        // When
        List<FireStation> result = fireStationServiceMock.findByStationNumber(stationNumber);
        // Assert
        Assertions.assertEquals(1, result.size(), "Expected 1 fire stations to be returned");
    }

    @Test
    public void addNewFireStationMock(){
        FireStation fireStationObject = new FireStation("123 Main St", "23");
        List<FireStation> existingStations = new ArrayList<>();

        when(fireStationRepositoryMock.processJSONFireStation()).thenReturn(existingStations);
        fireStationServiceMock.init();
        // When
        List<FireStation> result = fireStationServiceMock.addNewFireStation(fireStationObject);
       // Then
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("123 Main St", result.get(0).getAddress());
        Assertions.assertEquals("23", result.get(0).getStation());

        verify(fireStationRepositoryMock, times(1)).processJSONFireStation();

    }

    @Test
    public void testFindByStationNumber_ReturnsMatchingStations() {
        // Arrange
        String stationNumber = "3";
        FireStation fs1 = new FireStation("123 Main St", "3");
        FireStation fs2 = new FireStation("456 Elm St", "3");
        FireStation fs3 = new FireStation("456 Delaware", "9");

        List<FireStation> expectedList = List.of(fs1, fs2);

        when(fireStationRepositoryMock.findByStationId(stationNumber)).thenReturn(expectedList);
        fireStationServiceMock.init();

        // Act
        List<FireStation> result = fireStationServiceMock.findByStationNumber(stationNumber);

        System.out.println(result);
        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("123 Main St", result.get(0).getAddress());
        Assertions.assertEquals("456 Elm St", result.get(1).getAddress());

    }
    @Test
    public void updateFireStationTest(){
        // Arrange
        String stationNumber = "3";
        FireStation fs1 = new FireStation("123 Main St", "3");
        FireStation fs2 = new FireStation("123 Main St", "9");

        List<FireStation> existingFireStationList = new ArrayList<>();
        existingFireStationList.add(fs1);
        when(fireStationRepositoryMock.processJSONFireStation()).thenReturn(existingFireStationList);
        fireStationServiceMock.init();

        boolean result = fireStationServiceMock.updateFireStation(fs2,"123 Main St");
        List<FireStation> stationResult = fireStationServiceMock.getAllFireStation();
        System.out.println(stationResult);

        Assertions.assertTrue(result);
        Assertions.assertEquals("123 Main St", stationResult.get(0).getAddress());
        Assertions.assertEquals("9", stationResult.get(0).getStation());


    }

    @Test
    public void FindByStationNumber_ReturnsMatchingAddress() {
        // Arrange
        String stationNumber = "3";
        String stationAddress = "456 Delaware";
        //FireStation fs1 = new FireStation("123 Main St", "3");
        FireStation fs2 = new FireStation("456 Delaware", "3");
        FireStation fs3 = new FireStation("456 Delaware", "9");


        List<FireStation> expectedList = List.of(fs2,fs3);

        when(fireStationRepositoryMock.findStationByAddress(stationAddress)).thenReturn(stationNumber);
        fireStationServiceMock.init();

        // Act
        String result = fireStationServiceMock.findStationNumberbyAddress(stationAddress);
        System.out.println(result);
        // Assert
        Assertions.assertEquals("3", result);
  }

    @Test
    public void deleteStaionByAddressTest() {
        // Arrange
        String stationNumber = "3";
        String stationAddress = "456 Delaware";
        fireStationServiceMock.fireStationList = new ArrayList<>();

        fireStationServiceMock.fireStationList = new ArrayList<>();
        fireStationServiceMock.fireStationList.add(new FireStation("123 Main St", "3"));
        fireStationServiceMock.fireStationList.add(new FireStation("456 Delaware", "3"));

        //List<FireStation> fireStationList = Arrays.asList(fs1, fs2);
        //fireStationServiceMock.init();

        // Act
        boolean result = fireStationServiceMock.deleteByAddress(stationAddress);
        System.out.println(result);
        // Assert
        Assertions.assertEquals(true, result);
    }


}
