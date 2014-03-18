package coursework.common;

import java.util.Arrays;

/**
 * @author adkozlov
 */
public class Signature {

    private final byte[] bytes;

    public Signature(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Signature)) return false;

        Signature signature = (Signature) o;

        if (!Arrays.equals(bytes, signature.bytes)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }
}
