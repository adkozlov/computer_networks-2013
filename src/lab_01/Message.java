package lab_01;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;

public class Message {

    public static final Charset UTF8_CHARSET = Charset.forName("UTF8");
    public static final int BUFFER_LENGTH = 40;

    private final int ip;
    private final String computerName;
    private final long time;
    private final String lastName;

    public Message(byte[] bytes) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        ip = readBigIntegerFromStream(objectInputStream, 4).intValue();
        computerName = readStringFromStream(objectInputStream, 10);
        time = readBigIntegerFromStream(objectInputStream, 8).longValue();
        lastName = readStringFromStream(objectInputStream, 10);
    }

    public Message(int ip, String computerName, long time, String lastName) {
        this.ip = ip;
        this.computerName = computerName;
        this.time = time;
        this.lastName = lastName;
    }

    private static BigInteger readBigIntegerFromStream(final ObjectInputStream stream, int k) throws IOException {
        return new BigInteger(readKBytesFromStream(stream, k));
    }

    private static String readStringFromStream(final ObjectInputStream stream, int k) throws IOException {
        return new String(readKBytesFromStream(stream, k), UTF8_CHARSET);
    }

    private static byte[] readKBytesFromStream(final ObjectInputStream stream, int k) throws IOException {
        byte[] result = new byte[k];
        stream.read(result, 0, k);
        return result;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeInt(ip);
        objectOutputStream.write(computerName.getBytes(UTF8_CHARSET));
        objectOutputStream.writeLong(time);
        objectOutputStream.write(lastName.getBytes(UTF8_CHARSET));

        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public String getLastName() {
        return lastName;
    }

    public long getTime() {
        return time;
    }
}
