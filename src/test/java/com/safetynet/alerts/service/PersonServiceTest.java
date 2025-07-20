package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository personRepositoryMock;

    @InjectMocks
    PersonService personServiceMock;

    @Test
    void getAllPersonTest() {
        //Arrange
        Person p1 =    new Person( "Clive", "Ferguson", "748 Townings Dr", "Culver",
                "97451", "841-874-6741", "clivfd@ymail.com" );
        Person p2 = new Person( "Eric", "Cadigan", "951 LoneTree Rd", "Culver",
               "97451", "841-874-7458", "gramps@email.com" );
        //Act
        List<Person> mockData =  Arrays.asList(p1, p2);
        when(personRepositoryMock.processJSONPerson()).thenReturn(mockData);
        personServiceMock.init();
        List<Person> result = personServiceMock.getAllPerson();
        System.out.println(result);

        // Assert
        Assertions.assertEquals(result.size() , 2);


    }

    @Test
    void processAllEmail() {
        // Arrange
        List<String> expectedEmails = Arrays.asList("clivfd@ymail.com", "gramps@email.com");
        when(personRepositoryMock.processAllEmail("Culver")).thenReturn(expectedEmails);

        // Act
        List<String> result = personServiceMock.processAllEmail("Culver");

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains("clivfd@ymail.com"));

    }

    @Test
    void addPersonTest() {
        // Arrange: initial data
        Person original = new Person("Cutie", "Doe", "123 Main St", "CityA", "12345", "111-111-1111", "cutie@old.com");
        Person newRecord = new Person("Master", "Richie", "456 New St", "CityB", "54321", "222-222-2222", "cutie@new.com");
        List<Person> testList = new ArrayList<>();
        testList.add(original);
        when(personRepositoryMock.processJSONPerson()).thenReturn(testList);
        personServiceMock.init();

        List<Person> result = personServiceMock.addPerson(newRecord);
        // Assert
        Assertions.assertEquals(result.size() , 2);


    }

    @Test
    void updatePersonTest() {
        // Arrange: initial data
        Person original = new Person("Cutie", "Doe", "123 Main St", "CityA", "12345", "111-111-1111", "cutie@old.com");
        Person updated = new Person("Cutie", "Doe", "456 New St", "CityB", "54321", "222-222-2222", "cutie@new.com");

        List<Person> testList = Arrays.asList(original);

        // Manually set personList via reflection or setter if needed
        when(personRepositoryMock.processJSONPerson()).thenReturn(testList);
        personServiceMock.init();
        // Act
         personServiceMock.updatePerson(updated, "Cutie", "Doe");
        List<Person> result = personServiceMock.getAllPerson();

        // Assert
        assertEquals("456 New St", result.get(0).getAddress());
        assertEquals("CityB", result.get(0).getCity());
        assertEquals("cutie@new.com", result.get(0).getEmail());

    }
}