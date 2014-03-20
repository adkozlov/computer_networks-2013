package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class Verdict extends SignedObject {

    private final String studentName;
    private final String taskName;
    private final boolean accepted;
    private final String comments;

    public Verdict(String studentName, String taskName, boolean accepted, String comments, Signature signature) {
        super(signature);
        this.studentName = studentName;
        this.taskName = taskName;
        this.accepted = accepted;
        this.comments = comments;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getComments() {
        return comments;
    }
}
