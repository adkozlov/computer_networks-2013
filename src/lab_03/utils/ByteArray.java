package lab_03.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author adkozlov
 */
public abstract class ByteArray {

    protected final byte[] bytes;

    protected ByteArray(byte[] bytes, int length) {
        assert bytes.length == length;

        this.bytes = bytes;
    }

    protected static byte[] readFromStream(DataInputStream dis, int length) throws IOException {
        byte[] result = new byte[length];

        for (int i = 0; i < result.length; i++) {
            result[i] = dis.readByte();
        }

        return result;
    }

    public void writeToStream(DataOutputStream dos) throws IOException {
        for (byte b : bytes) {
            dos.writeByte(b);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public BigInteger bigIntegerValue() {
        return new BigInteger(bytes);
    }

    public static byte[] makeArray(byte[] bytes, int length) {
        assert bytes.length <= length;

        byte[] result = new byte[length];
        System.arraycopy(bytes, 0, result, length - bytes.length, bytes.length);

        return result;
    }
}
