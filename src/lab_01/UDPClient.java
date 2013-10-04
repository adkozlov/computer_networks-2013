package lab_01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class UDPClient implements Runnable {

    public static final int NAME_LENGTH = 10;

    private final String computerName;
    private final String lastName;
    private final InetAddress broadcastHost;
    private final InetAddress localHost;
    private final int ip;

    public UDPClient(String computerName, String lastName) throws UnknownHostException {
        String tempComputerName = new String(computerName);
        while (tempComputerName.length() < NAME_LENGTH) {
            tempComputerName += ' ';
        }

        String tempLastName = new String(lastName);
        while (tempLastName.length() < NAME_LENGTH) {
            tempLastName += ' ';
        }

        this.computerName = tempComputerName;
        this.lastName = tempLastName;

        localHost = InetAddress.getLocalHost();
        broadcastHost = InetAddress.getByName("255.255.255.255");

        ip = intFromBytes(localHost.getAddress());
    }

    public static int intFromBytes(byte[] bytes) {
        int result = 0;

        for (byte b : bytes) {
            result <<= 8;
            result |= b;
        }

        return result;
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

                        String message = "" + ip + '\u0000' + computerName + '\u0000' + System.currentTimeMillis() + '\u0000' + lastName;
                        byte[] buffer = message.getBytes();

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
