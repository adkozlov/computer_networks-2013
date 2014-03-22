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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignedObject)) return false;

        SignedObject that = (SignedObject) o;

        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return signature != null ? signature.hashCode() : 0;
    }
}
