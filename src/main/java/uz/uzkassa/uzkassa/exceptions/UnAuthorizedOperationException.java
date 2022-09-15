package uz.uzkassa.uzkassa.exceptions;

public class UnAuthorizedOperationException extends RuntimeException {
    public UnAuthorizedOperationException(String s) {
        super(s);
    }
}
