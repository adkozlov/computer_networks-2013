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

    public Verdict(Verdict verdict, Signature signature) {
        super(signature);
        studentName = verdict.getStudentName();
        taskName = verdict.getTaskName();
        accepted = verdict.isAccepted();
        comments = verdict.getComments();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Verdict)) return false;

        Verdict verdict = (Verdict) o;

        if (accepted != verdict.accepted) return false;
        if (comments != null ? !comments.equals(verdict.comments) : verdict.comments != null) return false;
        if (studentName != null ? !studentName.equals(verdict.studentName) : verdict.studentName != null) return false;
        if (taskName != null ? !taskName.equals(verdict.taskName) : verdict.taskName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentName != null ? studentName.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (accepted ? 1 : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
