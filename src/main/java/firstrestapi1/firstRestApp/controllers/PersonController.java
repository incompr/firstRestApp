package firstrestapi1.firstRestApp.controllers;

import firstrestapi1.firstRestApp.dto.PersonDTO;
import firstrestapi1.firstRestApp.exceptions.PersonNotCreatedException;
import firstrestapi1.firstRestApp.exceptions.PersonNotFoundException;
import firstrestapi1.firstRestApp.models.Person;
import firstrestapi1.firstRestApp.services.PersonService;
import firstrestapi1.firstRestApp.utils.PersonErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService peopleService;
    private final ModelMapper modelMapper;
    @Autowired
    public PersonController(PersonService peopleService, ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {

        return peopleService.findAll().stream()
                .map(this::converToPersonDTO)
                .collect(Collectors.toList());//Jackson автоматически конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {

        return converToPersonDTO(peopleService.findOne(id));        //джексон автоматически конвертирует в джейсон
    }


    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());

        }
        peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException ex) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person not found by id",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException ex) {
        PersonErrorResponse response = new PersonErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//400

    }

    private Person convertToPerson(PersonDTO personDTO) {

     //   ModelMapper modelMapper = new ModelMapper();

     /*   Person person = new Person();
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());*/

        return modelMapper.map(personDTO, Person.class);
    }
    private PersonDTO converToPersonDTO(Person person){
        return  modelMapper.map(person, PersonDTO.class);
    }



}
