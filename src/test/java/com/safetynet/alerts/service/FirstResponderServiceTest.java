package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.view.AgeGroupingView;
import com.safetynet.alerts.view.FirstResponderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FirstResponderServiceTest {

    @Mock
    private FireStationService fireStationServiceMock;
    @Mock
    private PersonService personServiceMock;
    @Mock
    private MedicalRecordService medicalRecordServiceMock;

    @InjectMocks
    private FirstResponderService firstResponderServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFireStationAddressTest(){
        //Arrange
        // Arrange
        FireStation fs1 = new FireStation("1509 Culver St", "3");
        FireStation fs2 = new FireStation("29 15th St", "2");
        FireStation fs3 = new FireStation("834 Binoc Ave", "3");

        List<FireStation> mockFireStations = Arrays.asList(fs1, fs2, fs3);

        when(fireStationServiceMock.getAllFireStation()).thenReturn(mockFireStations);

        List<String> result = firstResponderServiceMock.getFireStationAddress("3");

        // Assert
        assertEquals(2, result.size());
        assertEquals("1509 Culver St", result.get(0));
        assertEquals("834 Binoc Ave", result.get(1));

    }

    @Test
    void getChildrenListinAddressTest(){
        //Arrange
        Person p1 =    new Person( "John", "Boyd", "951 LoneTree Rd", "Culver",
                "97451", "841-874-6741", "clivfd@ymail.com" );
        Person p2 = new Person( "Jane", "Smith", "951 LoneTree Rd", "Culver",
                "97451", "841-874-7458", "gramps@email.com" );
        List<Person> personMockData = Arrays.asList(p1,p2);

        MedicalRecord adultRecord = new MedicalRecord(
                "John", "Boyd", "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord childRecord = new MedicalRecord(
                "Jane", "Smith", "03/06/2015",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        //List<MedicalRecord> medicalMockData = Arrays.asList(m1,m2);

        when(personServiceMock.findChildByAddress("951 LoneTree Rd")).thenReturn(personMockData);
        when(medicalRecordServiceMock.getMedicalRecordByFullName("John", "Boyd")).thenReturn(adultRecord);
        when(medicalRecordServiceMock.getMedicalRecordByFullName("Jane", "Smith")).thenReturn(childRecord);

        List<FirstResponderView> result = firstResponderServiceMock.getChildrenListinAddress("951 LoneTree Rd");
        //System.out.println(result);

        assertEquals(2, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
        assertEquals("John", result.get(1).getFirstName());
    }

    @Test
    void getPeopleListinAddressTest(){
        // Arrange
        String add1 = "1509 Culver St";
        String add2 = "29 15th St";
        String add3 = "834 Binoc Ave";

        List<String> addressList = Arrays.asList(add1,add2,add3);

        Person p1 =    new Person( "John", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6741", "clivfd@ymail.com" );
        Person p2 = new Person( "Jane", "Smith", "834 Binoc Ave", "Culver",
                "97451", "841-874-7458", "gramps@email.com" );
        List<Person> personMockData = Arrays.asList(p1,p2);

        MedicalRecord adultRecord = new MedicalRecord(
                "John", "Boyd", "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        MedicalRecord childRecord = new MedicalRecord(
                "Jane", "Smith", "03/06/2015",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")
        );
        List<MedicalRecord> medicalMockData = Arrays.asList(adultRecord,childRecord);
        when(fireStationServiceMock.getAllFireStation()).thenReturn(List.of(
                new FireStation("1509 Culver St", "3"),
                new FireStation("29 15th St", "3"),
                new FireStation("834 Binoc Ave", "3")
        ));
        when(personServiceMock.findPersonsByAddresses(addressList)).thenReturn(personMockData);
        when(medicalRecordServiceMock.getMedicalRecordByFullName("John", "Boyd")).thenReturn(adultRecord);
        when(medicalRecordServiceMock.getMedicalRecordByFullName("Jane", "Smith")).thenReturn(childRecord);

        AgeGroupingView ageGroupingList = firstResponderServiceMock.getPeopleListinAddress("3");

        // Assert
        assertNotNull(ageGroupingList);
        assertEquals(1, ageGroupingList.getAdultCount());
        assertEquals(1, ageGroupingList.getChildCount());
        assertEquals(2, ageGroupingList.getPersonList().size());

        FirstResponderView first = ageGroupingList.getPersonList().get(0);
        assertEquals("John", first.getFirstName());
        assertTrue(first.getAge() > 18);

        FirstResponderView second = ageGroupingList.getPersonList().get(1);
        assertEquals("Jane", second.getFirstName());
        assertTrue(second.getAge() <= 18);

    }
}
