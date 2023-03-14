package firstrestapi1.firstRestApp.services;


import firstrestapi1.firstRestApp.exceptions.PersonNotFoundException;
import firstrestapi1.firstRestApp.models.Person;
import firstrestapi1.firstRestApp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

  /*  public Person findOne(String name) {
  //      Optional<Person> foundPerson = personRepository.findByName(name);
  //      return foundPerson.orElseThrow(PersonNotFoundException::new);
  //  }*/

    @Transactional
    public void save(Person person){
        enrichPerson(person);
        personRepository.save(person);
    }

    private void enrichPerson(Person person) {


        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");

    }

}

