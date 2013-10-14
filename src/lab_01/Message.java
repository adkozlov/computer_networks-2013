package lab_01;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Message {

    public static final Charset UTF8_CHARSET = Charset.forName("UTF8");
    public static final int BUFFER_LENGTH = 52;

    private final int ip;
    private final String computerName;
    private final long time;
    private final String lastName;

    public Message(byte[] bytes) throws IOException {
        ip = readBigIntegerFromStream(bytes, 0, 4).intValue();
        computerName = readStringFromStream(bytes, 4, 24);
        time = readBigIntegerFromStream(bytes, 8, 32).longValue();
        lastName = readStringFromStream(bytes, 32, 52);
    }

    public Message(int ip, String computerName, long time, String lastName) {
        this.ip = ip;
        this.computerName = computerName;
        this.time = time;
        this.lastName = lastName;
    }

    private static BigInteger readBigIntegerFromStream(byte[] bytes, int from, int to) throws IOException {
        return new BigInteger(Arrays.copyOfRange(bytes, from, to));
    }

    private static String readStringFromStream(byte[] bytes, int from, int to) throws IOException {
        return new String(Arrays.copyOfRange(bytes, from, to), UTF8_CHARSET);
    }

    public byte[] toByteArray() throws IOException {
        byte[] buffer = new byte[Message.BUFFER_LENGTH];

        byte[] ipBuffer = BigInteger.valueOf(ip).toByteArray();
        for (int i = 0; i < ipBuffer.length; i++) {
            buffer[i] = ipBuffer[i];
        }

        byte[] computerNameBuffer = computerName.getBytes(UTF8_CHARSET);
        for (int i = 0; i < computerNameBuffer.length; i++) {
            buffer[4 + i] = computerNameBuffer[i];
        }

        byte[] timeBuffer = BigInteger.valueOf(time).toByteArray();
        for (int i = 0; i < timeBuffer.length; i++) {
            buffer[24 + i] = timeBuffer[i];
        }

        byte[] lastNameBuffer = lastName.getBytes(UTF8_CHARSET);
        for (int i = 0; i < lastNameBuffer.length; i++) {
            buffer[32 + i] = lastNameBuffer[i];
        }

        return buffer;
    }

    public String getLastName() {
        return lastName;
    }

    public long getTime() {
        return time;
    }
}
