package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class Task extends SignedObject {

    private final String name;
    private final String text;
    private final long deadline;

    public Task(String name, String text, long deadline, Signature signature) {
        super(signature);
        this.name = name;
        this.text = text;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public long getDeadline() {
        return deadline;
    }
}
