package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class SolutionResponse extends SignedObject {

    private final boolean accepted;
    private final String comments;

    public SolutionResponse(boolean accepted, String comments, Signature signature) {
        super(signature);
        this.accepted = accepted;
        this.comments = comments;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getComments() {
        return comments;
    }
}
