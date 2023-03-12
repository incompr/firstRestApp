package firstrestapi1.firstRestApp.exceptions;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String message){
        super(message);
    }
}
