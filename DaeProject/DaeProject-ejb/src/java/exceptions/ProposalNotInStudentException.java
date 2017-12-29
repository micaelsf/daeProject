package exceptions;

public class ProposalNotInStudentException extends Exception {

    public ProposalNotInStudentException() {
    }

    public ProposalNotInStudentException(String msg) {
        super(msg);
    }
}
