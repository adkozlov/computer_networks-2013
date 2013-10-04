package lab_01;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;

public class Message {

    public static final Charset UTF8_CHARSET = Charset.forName("UTF8");

    private final int ip;
    private final String computerName;
    private final long time;
    private final String lastName;

    public Message(byte[] bytes) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        ip = new BigInteger(readKBytesFromStream(objectInputStream, 4)).intValue();
        computerName = new String(readKBytesFromStream(objectInputStream, 10), UTF8_CHARSET);
        time = new BigInteger(readKBytesFromStream(objectInputStream, 8)).longValue();
        lastName = new String(readKBytesFromStream(objectInputStream, 10), UTF8_CHARSET);
    }

    public Message(int ip, String computerName, long time, String lastName) {
        this.ip = ip;
        this.computerName = computerName;
        this.time = time;
        this.lastName = lastName;
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
