package kmd.backend.magazine.exceptions;


public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String e){
        super(e+" is not found!");
    }
}
