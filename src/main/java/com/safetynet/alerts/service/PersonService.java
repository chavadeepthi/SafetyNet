package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {


    //private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    //private final JsonFileReadService jsonFileReadService;
    //private List<Person> personList;
    PersonRepository personRepository;
    private List<Person> personList = new ArrayList<>();


    @Autowired //Optional for single constructor
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @PostConstruct
    public void init() {
        personList = personRepository.processJSONPerson();
    }
    public List<Person> getAllPerson() {
        return personList;
    }

    public List<String> processAllEmail(String city) {

     return personRepository.processAllEmail(city);
    }
    public List<Person> addPerson(Person personObject) {
        //List<Person> personList = getAllPerson();
        //Person existingPerson = personRepository.findByFullName(personObject.getFirstName(), personObject.getLastName());
        personList.add(personObject);
        return personList;
    }
    private Person findByFullName(String firstName, String lastName) {
        for (Person p : personList) {
            if (p.getFirstName().equalsIgnoreCase(firstName) &&
                    p.getLastName().equalsIgnoreCase(lastName)) {
                return p;
            }
        }
        return null;
    }

    public boolean deleteByFirstname(String firstname, String lastname) {

        Iterator<Person> iterator = personList.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            Person fs = iterator.next();
            if (fs.getFirstName().equalsIgnoreCase(firstname) && fs.getLastName().equalsIgnoreCase(lastname)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        // Optionally persist the updated list here if needed
        return removed;
    }

    public boolean updatePerson(Person updatePerson, String firstName, String lastName)
    {
        boolean updated = false;
        //Person existingPerson = personRepository.findByFullName(updatePerson.getFirstName(), updatePerson.getLastName());

        for(Person pd : personList){
            log.info("Checking person: {}", pd.getFirstName());  //
            if (pd.getFirstName().equalsIgnoreCase(firstName) && pd.getLastName().equalsIgnoreCase(lastName)) {
                log.info("Match found. Updating person: {}", pd);
                pd.setAddress(updatePerson.getAddress());
                pd.setCity(updatePerson.getCity());
                pd.setEmail(updatePerson.getEmail());
                pd.setPhone(updatePerson.getPhone());
                pd.setZip(updatePerson.getZip());

                updated = true;

            }

        }
        return updated;
    }

    public List<Person> findPersonsByAddresses(List<String> addresses) {
        List<Person> allPersons = personRepository.processJSONPerson();

        return allPersons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());
    }

    public List<Person> findChildByAddress(String address) {
        return findPersonsByAddresses(Collections.singletonList(address));
    }

}
