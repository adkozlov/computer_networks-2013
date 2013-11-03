package lab_02;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author adkozlov
 */
public class TCPMessage implements IMessage, Comparable<TCPMessage> {

    public static final Charset UTF8_CHARSET = Charset.forName("UTF8");
    public static final String MESSAGE_FORMAT = "%s at %s wrote:\t\n%s\n";

    public TCPMessage(long timeStamp, byte[] mac, String message) {
        this.timeStamp = timeStamp;
        this.mac = new MacAddress(mac);
        this.message = message;
    }

    public TCPMessage(TCPMessage tcpMessage, long offset) {
        timeStamp = tcpMessage.timeStamp + offset;
        mac = tcpMessage.mac;
        message = tcpMessage.message;
    }

    public TCPMessage(String message) throws UnknownHostException, SocketException {
        this.message = message;
        mac = new MacAddress(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress());
        timeStamp = System.currentTimeMillis();
    }

    private final long timeStamp;
    private final MacAddress mac;
    private final String message;

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public MacAddress getMac() {
        return mac;
    }

    public static TCPMessage fromByteArray(byte[] bytes) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        long timeStamp = dis.readLong();

        byte[] mac = new byte[6];
        for (int i = 0; i < mac.length; i++) {
            mac[i] = dis.readByte();
        }

        int length = dis.read();
        byte[] message = new byte[length];
        dis.readFully(message);

        return new TCPMessage(timeStamp, mac, new String(message, UTF8_CHARSET));
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeLong(timeStamp);
        dos.write(mac.getMac());

        byte[] bytes = message.getBytes(UTF8_CHARSET);
        dos.write(bytes.length);
        dos.write(bytes);

        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return String.format(MESSAGE_FORMAT, getMac(), new Date(getTimeStamp()), getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TCPMessage)) return false;

        TCPMessage message1 = (TCPMessage) o;

        if (timeStamp != message1.timeStamp) return false;
        if (mac != null ? !mac.equals(message1.mac) : message1.mac != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(TCPMessage o) {
        if (timeStamp != o.timeStamp) {
            return new Long(timeStamp).compareTo(o.timeStamp);
        } else {
            return message.compareTo(o.message);
        }
    }
}
