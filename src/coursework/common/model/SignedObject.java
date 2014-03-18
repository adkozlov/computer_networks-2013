package coursework.common.model;

import coursework.common.Signature;

/**
 * @author adkozlov
 */
public class SignedObject {

    private final Signature signature;

    public SignedObject(Signature signature) {
        this.signature = signature;
    }

    public Signature getSignature() {
        return signature;
    }
}
