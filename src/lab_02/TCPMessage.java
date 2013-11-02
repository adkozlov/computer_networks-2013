package lab_02;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 * @author adkozlov
 */
public class TCPMessage implements IMessage {

    public static final Charset UTF8_CHARSET = Charset.forName("UTF8");

    public TCPMessage(String message, long timeStamp) throws UnknownHostException, SocketException {
        this.message = message;
        mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        this.timeStamp = timeStamp;
    }

    private final long timeStamp;
    private final byte[] mac;
    private final String message;

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public static TCPMessage fromByteArray(byte[] bytes) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        long timeStamp = dis.readLong();
        byte[] mac = new byte[6];
        dis.read(mac, 8, 6);
        int length = dis.read();
        byte[] message = new byte[length];
        dis.readFully(message);

        return new TCPMessage(new String(message, UTF8_CHARSET), timeStamp);
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeLong(timeStamp);
        dos.write(mac);
        dos.writeBytes(message);

        return baos.toByteArray();
    }
}
