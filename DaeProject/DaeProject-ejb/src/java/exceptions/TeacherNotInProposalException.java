package exceptions;

public class TeacherNotInProposalException extends Exception {

    public TeacherNotInProposalException() {
    }

    public TeacherNotInProposalException(String msg) {
        super(msg);
    }
}
