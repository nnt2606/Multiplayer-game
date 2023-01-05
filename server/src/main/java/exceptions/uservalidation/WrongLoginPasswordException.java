package exceptions.uservalidation;

public class WrongLoginPasswordException extends Exception {
    public WrongLoginPasswordException() {
        super("ERROR: Wrong password received");
    }
}