package coursework.common.model;

/**
 * @author adkozlov
 */
public class SolutionResponse {

    private final boolean accepted;
    private final String comments;

    public SolutionResponse(boolean accepted, String comments) {
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
