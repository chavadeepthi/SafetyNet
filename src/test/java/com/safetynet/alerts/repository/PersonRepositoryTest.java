package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class PersonRepositoryTest {
    @Mock
    private JsonFileReadRespository jsonFileReadRespositoryMock;

    @InjectMocks
    private PersonRepository personRepositoryMock;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes @Mock and @InjectMocks
    }

    @Test
    void processJSONPersonTest_success() throws Exception {
        String json = """
                {
                    "persons": [
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "city": "Culver",
                            "email": "john@example.com"
                        }
                    ]
                }
                """;

        JsonNode rootNode = objectMapper.readTree(json);
        when(jsonFileReadRespositoryMock.getRootNode()).thenReturn(rootNode);
        when(jsonFileReadRespositoryMock.getObjectMapper()).thenReturn(objectMapper);

        List<Person> result = personRepositoryMock.processJSONPerson();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testFindByFullName_Found() throws Exception {
        // Same setup as previous test
        processJSONPersonTest_success();

        // Act
        Person person = personRepositoryMock.findByFullName("John", "Doe");

        // Assert
        assertNotNull(person);
        assertEquals("Culver", person.getCity());
    }
    @Test
    void testgetemaillist_Found() throws Exception {
        // Same setup as previous test
        processJSONPersonTest_success();

        // Act
        List<String> emailList = personRepositoryMock.processAllEmail("Culver");

        // Assert
        assertNotNull(emailList);
        assertEquals("john@example.com", emailList.get(0));
    }

}
