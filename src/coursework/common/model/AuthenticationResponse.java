package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class AuthenticationResponse {

    private final boolean passed;
    private final String name;
    private final Signature signature;

    public AuthenticationResponse(String name, Signature signature) {
        passed = true;
        this.name = name;
        this.signature = signature;
    }

    public AuthenticationResponse() {
        passed = false;
        name = null;
        signature = null;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getName() {
        return name;
    }

    public Signature getSignature() {
        return signature;
    }
}
