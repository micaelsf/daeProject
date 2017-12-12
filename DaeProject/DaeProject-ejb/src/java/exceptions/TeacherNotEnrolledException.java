package exceptions;

public class TeacherNotEnrolledException extends Exception {

    public TeacherNotEnrolledException() {
    }

    public TeacherNotEnrolledException(String msg) {
        super(msg);
    }
}
