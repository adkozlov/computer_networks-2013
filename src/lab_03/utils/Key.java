package lab_03.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author adkozlov
 */
public class Key extends ByteArray implements Comparable<Key> {

    private static final int ARRAY_LENGTH = 128;

    public Key(byte[] bytes) {
        super(bytes, ARRAY_LENGTH);
    }

    public static Key readFromStream(DataInputStream dis) throws IOException {
        return new Key(readFromStream(dis, ARRAY_LENGTH));
    }

    @Override
    public int compareTo(Key o) {
        return bigIntegerValue().compareTo(o.bigIntegerValue());
    }

    public static Key fromByteArray(byte[] bytes) {
        return new Key(makeArray(bytes, ARRAY_LENGTH));
    }

    public static Key fromBigInteger(BigInteger integer) {
        return fromByteArray(integer.toByteArray());
    }
}
