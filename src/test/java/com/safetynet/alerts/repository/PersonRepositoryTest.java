package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
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

public class PersonRepositoryTest {
    @Mock
    public JsonFileReadRespository jsonFileReadRespositoryMock;

    @InjectMocks
    public PersonRepository personRepositoryMock;

    public ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        personRepositoryMock = new PersonRepository(jsonFileReadRespositoryMock, objectMapper);// Initializes @Mock and @InjectMocks
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
    @Test
    void writePersonRecordtoJSON_Success() throws Exception {
        // Arrange
        processJSONPersonTest_success();

        List<Person> updatedList = List.of(
                new Person(
                        "Smart",
                        "Pants",
                        "489 Manchester St",
                        "Culver",
                        "841-874-9845",
                        "smart@email.com",
                        "97451"
                ),
                new Person(
                        "Barbie",
                        "Doll",
                        "489 Delaware St",
                        "Culver",
                        "841-874-9845",
                        "smart@email.com",
                        "97451"
                )
        );// new one


        // We want to capture what gets passed to writeToJsonFile
        ArgumentCaptor<JsonNode> captor = ArgumentCaptor.forClass(JsonNode.class);

        // Act
        personRepositoryMock.writePersontoJSON(updatedList);

        // Assert
        verify(jsonFileReadRespositoryMock, times(1)).writeToJsonFile(captor.capture());

        JsonNode writtenRoot = captor.getValue();

        // The root should contain 2 firestations now
        assertEquals(2, writtenRoot.get("persons").size());
        assertEquals("Pants", writtenRoot.get("persons").get(0).get("lastName").asText());
        assertEquals("Doll", writtenRoot.get("persons").get(1).get("lastName").asText());
    }

}
