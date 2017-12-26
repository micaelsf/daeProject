package exceptions;

public class ProposalNotInTeacherException extends Exception {

    public ProposalNotInTeacherException() {
    }

    public ProposalNotInTeacherException(String msg) {
        super(msg);
    }
}
