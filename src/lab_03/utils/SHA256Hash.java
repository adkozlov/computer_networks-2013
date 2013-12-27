package lab_03.utils;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author adkozlov
 */
public class SHA256Hash extends ByteArray {

    private static final int ARRAY_LENGTH = 32;

    public SHA256Hash(byte[] bytes) {
        super(bytes, ARRAY_LENGTH);
    }

    public static SHA256Hash readFromStream(DataInputStream dis) throws IOException {
        return new SHA256Hash(readFromStream(dis, ARRAY_LENGTH));
    }
}
