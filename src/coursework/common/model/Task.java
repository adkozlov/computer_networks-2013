package coursework.common.model;

/**
 * @author adkozlov
 */
public class Task {

    private final String text;
    private final long deadline;

    public Task(String text, long deadline) {
        this.text = text;
        this.deadline = deadline;
    }

    public String getText() {
        return text;
    }

    public long getDeadline() {
        return deadline;
    }
}
