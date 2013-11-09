package lab_02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class UDPAnnouncer implements Runnable {

    private static final UDPAnnouncer INSTANCE = new UDPAnnouncer();

    public static UDPAnnouncer getInstance() {
        return INSTANCE;
    }

    private UDPAnnouncer() {
        try {
            broadcastHost = InetAddress.getByName(BROADCAST_IP);
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        }
    }

    public static final long PERIOD = 5000;
    public static final int UDP_PORT = 1235;
    public static final String BROADCAST_IP = "255.255.255.255";

    private InetAddress broadcastHost;

    @Override
    public synchronized void run() {
        if (Thread.currentThread().isAlive()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();

                        byte[] buffer = new UDPAnnounce().toByteArray();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastHost, UDP_PORT);
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
