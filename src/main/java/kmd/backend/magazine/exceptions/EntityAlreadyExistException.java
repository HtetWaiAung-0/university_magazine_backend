package kmd.backend.magazine.exceptions;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(String e){
        super(e+" is already exist!");
    }
}

