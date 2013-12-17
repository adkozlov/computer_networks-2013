package lab_03;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author adkozlov
 */
public final class UDPAnnouncer implements Runnable {

    private static final UDPAnnouncer INSTANCE = new UDPAnnouncer();

    public static UDPAnnouncer getInstance() {
        return INSTANCE;
    }

    private UDPAnnouncer() {
    }

    public static final long PERIOD = 5000;
    public static final int UDP_PORT = 3012;
    public static final String BROADCAST_IP = "255.255.255.255";

    @Override
    public void run() {
        if (Thread.currentThread().isAlive()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();

                        byte[] buffer = new UDPAnnounce().toByteArray();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(BROADCAST_IP), UDP_PORT);
                        clientSocket.send(packet);

                        clientSocket.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }, 0, PERIOD);
        }
    }
}
