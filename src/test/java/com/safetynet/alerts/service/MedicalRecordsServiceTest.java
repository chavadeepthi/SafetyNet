package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest {
    @Mock
    MedicalRecordRepository medicalRecordRepositoryMock;

    @InjectMocks
    MedicalRecordService medicalRecordServiceMock;

    @Test
    void getAllMedicalRecordsTest(){
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
        List<MedicalRecord> mockData = Arrays.asList(m1,m2);
        when(medicalRecordRepositoryMock.processJSONMedicalRecord()).thenReturn(mockData);
        medicalRecordServiceMock.init();
        List<MedicalRecord> result = medicalRecordServiceMock.getAllMedicalRecords();
        System.out.println(result);

        // Assert
        Assertions.assertEquals(result.size() , 2);
    }

    @Test
    void addMedicalRecordsTest(){
        //Arrange
        MedicalRecord existing = new MedicalRecord(
                "John", "Doe", "03/06/1999",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord newRec = new MedicalRecord(
                "John", "Smith", "03/06/1990",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        List<MedicalRecord> mockData = new ArrayList<>();
        mockData.add(existing);
        when(medicalRecordRepositoryMock.processJSONMedicalRecord()).thenReturn(mockData);
        medicalRecordServiceMock.init();
        List<MedicalRecord> result = medicalRecordServiceMock.addMedicalRecords(newRec);
        System.out.println(result);

        // Assert
        Assertions.assertEquals(result.size() , 2);
        //Assertions.assertTrue(result.contains("Smith"));
        assertEquals("Doe", result.get(0).getLastName());
    }
    @Test
    void updateMedicalRecordTest(){
        //Arrange
        MedicalRecord existing = new MedicalRecord(
                "John", "Doe", "03/06/1999",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord newRec = new MedicalRecord(
                "John", "Doe", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );
        List<MedicalRecord> mockData = new ArrayList<>();
        mockData.add(existing);
        when(medicalRecordRepositoryMock.processJSONMedicalRecord()).thenReturn(mockData);
        medicalRecordServiceMock.init();
         medicalRecordServiceMock.updateMedicalRecords(newRec, "John", "Doe");
        List<MedicalRecord> result = medicalRecordServiceMock.getAllMedicalRecords();
        System.out.println(result);

        // Assert
        Assertions.assertEquals(result.size() , 1);
        //Assertions.assertTrue(result.contains("Smith"));
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void deleteMedicalRecordTest(){
        //Arrange
        MedicalRecord rec1 = new MedicalRecord(
                "John", "Doe", "03/06/1999",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord rec2 = new MedicalRecord(
                "Bob", "Marley", "03/06/1990",
                List.of("aznol:3", "hydrapermazol:1"),
                List.of("nill")
        );
        List<MedicalRecord> mockData = new ArrayList<>();
        mockData.add(rec1);
        mockData.add(rec2);
        when(medicalRecordRepositoryMock.processJSONMedicalRecord()).thenReturn(mockData);
        medicalRecordServiceMock.init();
        medicalRecordServiceMock.deleteMedicalRecords("John", "Doe");
        List<MedicalRecord> result = medicalRecordServiceMock.getAllMedicalRecords();
        System.out.println(result);

        // Assert
        Assertions.assertEquals(result.size() , 1);
        //Assertions.assertTrue(result.contains("Smith"));
        assertEquals("Marley", result.get(0).getLastName());
    }
}
