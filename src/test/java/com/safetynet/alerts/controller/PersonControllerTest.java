package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
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

public class PersonControllerTest {
    @Mock
    PersonService personServiceMock;

    @InjectMocks
    PersonController personControllerMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllPersonTest(){
        Person p1 = new Person("John", "Doe", "123 St", "City", "12345", "123456", "john@example.com");
        when(personServiceMock.getAllPerson()).thenReturn(Arrays.asList(p1));

        List<Person> result = personControllerMock.getPersonList();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());

    }
    @Test
    public void updatePersonTest(){
       Person updated = new Person("Jane", "Smith", "456 St", "City", "54321", "987654", "jane@example.com");
        when(personServiceMock.updatePerson(updated, "Jane", "Smith")).thenReturn(true);

        ResponseEntity<String> response = personControllerMock.updatePerson("Jane", "Smith", updated);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("successfully"));

    }
    @Test
    void testUpdatePersonNotFound() {
        Person updated = new Person("Jane", "Smith", "456 St", "City", "54321", "987654", "jane@example.com");
        when(personServiceMock.updatePerson(updated, "Jane", "Smith")).thenReturn(false);

        ResponseEntity<String> response = personControllerMock.updatePerson("Jane", "Smith", updated);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("not found"));
    }
    @Test
    public void deletePersonTest(){
        when(personServiceMock.deleteByFirstname("Jane", "Smith")).thenReturn(true);

        ResponseEntity<String> response = personControllerMock.deletePerson("Jane", "Smith");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("successfully"));

    }
    @Test
    void testAddPerson() {
        Person p = new Person("Jane", "Smith", "456 St", "City", "54321", "987654", "jane@example.com");
        when(personServiceMock.addPerson(p)).thenReturn(Arrays.asList(p));

        List<Person> result = personControllerMock.addPerson(p);

        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
    }

    @Test
    public void deletePersonNotFoundTest(){
        when(personServiceMock.deleteByFirstname("Jane", "Smith")).thenReturn(false);

        ResponseEntity<String> response = personControllerMock.deletePerson("Jane", "Smith");

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("not found"));

    }

}
