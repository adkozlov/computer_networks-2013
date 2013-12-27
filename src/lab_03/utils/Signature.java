package lab_03.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author adkozlov
 */
public class Signature extends ByteArray {

    private static final int ARRAY_LENGTH = 256;

    protected Signature(byte[] bytes) {
        super(bytes, ARRAY_LENGTH);
    }

    public static Signature readFromStream(DataInputStream dis) throws IOException {
        return new Signature(readFromStream(dis, ARRAY_LENGTH));
    }

    private static Signature fromByteArrays(byte[] first, byte[] second) {
        byte[] result = new byte[ARRAY_LENGTH];

        System.arraycopy(first, 0, result, ARRAY_LENGTH / 2 - first.length, first.length);
        System.arraycopy(second, 0, result, ARRAY_LENGTH - second.length, second.length);

        return new Signature(result);
    }

    public static Signature fromBigIntegers(BigInteger r, BigInteger s) {
        return fromByteArrays(r.toByteArray(), s.toByteArray());
    }

    private BigInteger bigInteger(int i) {
        int half = ARRAY_LENGTH / 2;

        byte[] result = new byte[ARRAY_LENGTH / 2];
        System.arraycopy(bytes, half * i, result, 0, half);

        return new BigInteger(result);
    }

    public BigInteger firstBigInteger() {
        return bigInteger(0);
    }

    public BigInteger secondBigInteger() {
        return bigInteger(1);
    }
}
