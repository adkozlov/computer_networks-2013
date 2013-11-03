package lab_02;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author adkozlov
 */
public class UDPAnnounce implements IMessage {

    private final long currentTime;
    private final MacAddress mac;

    public UDPAnnounce(long currentTime, byte[] mac) {
        this.currentTime = currentTime;
        this.mac = new MacAddress(mac);
    }

    public UDPAnnounce() throws UnknownHostException, SocketException {
        currentTime = System.currentTimeMillis();
        mac = new MacAddress(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress());
    }

    public static UDPAnnounce fromByteArray(byte[] bytes) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        long currentTime = dis.readLong();
        byte[] mac = new byte[6];
        dis.readFully(mac);

        return new UDPAnnounce(currentTime, mac);
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeLong(currentTime);

        if (mac.getMac() != null) {
            dos.write(mac.getMac());
        } else {
            throw new IOException();
        }

        return baos.toByteArray();
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public MacAddress getMac() {
        return mac;
    }
}
