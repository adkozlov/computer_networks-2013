package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class Verdict extends SignedObject {

    private final String name;
    private final boolean accepted;
    private final String comments;

    public Verdict(String name, boolean accepted, String comments, Signature signature) {
        super(signature);
        this.name = name;
        this.accepted = accepted;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getComments() {
        return comments;
    }
}
