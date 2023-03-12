package firstrestapi1.firstRestApp.repositories;


import firstrestapi1.firstRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Neil Alishev
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}



