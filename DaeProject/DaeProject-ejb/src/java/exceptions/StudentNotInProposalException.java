package exceptions;

public class StudentNotInProposalException extends Exception {

    public StudentNotInProposalException() {
    }

    public StudentNotInProposalException(String msg) {
        super(msg);
    }
}
