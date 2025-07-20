package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.MedicalRecordsServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MedicalRecordControllerTest {
    @Mock
    MedicalRecordService medicalRecordServiceMock;

    @InjectMocks
    MedicalRecordController medicalRecordControllerMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMedicalRecordTest(){
        //Arrange
        MedicalRecord m1 = new MedicalRecord(
                "John", "Boyd", "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord m2 = new MedicalRecord(
                "John", "Smith", "03/06/1990",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );

        when(medicalRecordServiceMock.getAllMedicalRecords()).thenReturn(Arrays.asList(m1,m2));

        List<MedicalRecord> result = medicalRecordControllerMock.getAllMedicalRecords();

        assertEquals(2, result.size());
        assertEquals("Boyd", result.get(0).getLastName());
    }

    @Test
    void addMedicalRecordTest(){
        //Arrange

        MedicalRecord newRec = new MedicalRecord(
                "Jane", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );

        when(medicalRecordServiceMock.addMedicalRecords(newRec)).thenReturn(Arrays.asList(newRec));
        List<MedicalRecord> result = medicalRecordControllerMock.addMedicalRecords(newRec);
        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
    }

    @Test
    void updateMedicalRecordSuccessTest(){
        //Arrange

        MedicalRecord newRec = new MedicalRecord(
                "John", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );

        when(medicalRecordServiceMock.updateMedicalRecords(newRec,"John","Doe")).thenReturn(true);

        ResponseEntity<String> result =
                medicalRecordControllerMock.updateMedicalRecords
                        (newRec,"John","Doe");
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("successfully"));


    }
    @Test
    void updateMedicalRecordSuccessFail(){
        //Arrange

        MedicalRecord newRec = new MedicalRecord(
                "John", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );

        when(medicalRecordServiceMock.updateMedicalRecords(newRec,"John","Doe")).thenReturn(false);

        ResponseEntity<String> result =
                medicalRecordControllerMock.updateMedicalRecords
                        (newRec,"John","Doe");
        assertEquals(404, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("not found"));


    }

    @Test
    void deleteMedicalRecordSuccessTest(){
        //Arrange
        MedicalRecord MedRec = new MedicalRecord(
                "John", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );

        when(medicalRecordServiceMock.deleteMedicalRecords("John", "Doe")).thenReturn(true);
        ResponseEntity<String> result =
                medicalRecordControllerMock.deleteMedicalRecords("John","Doe");

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("successfully"));


    }
    @Test
    void deleteMedicalRecordFailTest(){
        //Arrange
        MedicalRecord MedRec = new MedicalRecord(
                "Jane", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );

        when(medicalRecordServiceMock.deleteMedicalRecords("John", "Doe")).thenReturn(false);
        ResponseEntity<String> result =
                medicalRecordControllerMock.deleteMedicalRecords("John","Doe");

        assertEquals(404, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("not found"));


    }
}
