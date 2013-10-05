package lab_01;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient implements Runnable {

    private static final String FORMAT = "%-10s";

    private final String computerName;
    private final String lastName;
    private final InetAddress broadcastHost;
    private final InetAddress localHost;
    private final int ip;

    public UDPClient(String computerName, String lastName) throws UnknownHostException {
        localHost = InetAddress.getLocalHost();
        ip = new BigInteger(localHost.getAddress()).intValue();
        broadcastHost = InetAddress.getByName("127.0.0.1");

        this.computerName = String.format(FORMAT, computerName);
        this.lastName = String.format(FORMAT, lastName);
    }

    public static final long PERIOD = 500;

    @Override
    public synchronized void run() {
        if (Thread.currentThread().isAlive()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();

                        byte[] buffer = new Message(ip, computerName, System.currentTimeMillis(), lastName).toByteArray();

                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastHost, UDPServer.PORT_NUMBER);
                        clientSocket.send(packet);

                        clientSocket.close();
                    } catch (IOException e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                }
            }, 0, PERIOD);
        }
    }
}
