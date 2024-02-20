package kmd.backend.magazine.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(){
        super("Server bad request!");
    }
}
