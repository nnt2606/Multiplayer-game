package exceptions.uservalidation;

public class RegisterPasswordMismatchException extends Exception {
    public RegisterPasswordMismatchException() {
        super("ERROR: Retype password mismatch!!!");
    }
}