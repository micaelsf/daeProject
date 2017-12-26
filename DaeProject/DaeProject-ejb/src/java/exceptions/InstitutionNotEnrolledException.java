package exceptions;

public class InstitutionNotEnrolledException extends Exception {

    public InstitutionNotEnrolledException() {
    }

    public InstitutionNotEnrolledException(String msg) {
        super(msg);
    }
}
