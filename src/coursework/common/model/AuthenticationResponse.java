package coursework.common.model;

/**
 * @author adkozlov
 */
public class AuthenticationResponse {

    private final boolean passed;
    private final String name;

    public AuthenticationResponse(String name) {
        passed = true;
        this.name = name;
    }

    public AuthenticationResponse() {
        passed = false;
        name = null;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getName() {
        return name;
    }
}
