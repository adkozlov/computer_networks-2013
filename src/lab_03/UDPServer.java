package lab_03;

import lab_03.messages.UDPMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author adkozlov
 */
public final class UDPServer implements Runnable {

    private static final UDPServer INSTANCE = new UDPServer();
    private static final int BUFFER_LENGTH = 4096;

    public static UDPServer getInstance() {
        return INSTANCE;
    }

    private UDPServer() {
        if (Thread.currentThread().isAlive()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    DatagramSocket udpSocket = null;

                    try {
                        udpSocket = new DatagramSocket(UDP_PORT);

                        while (true) {
                            byte[] buffer = new byte[BUFFER_LENGTH];
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                            try {
                                udpSocket.receive(packet);
                                UDPMessage announce = UDPMessage.fromByteArray(packet.getData());
                                // TODO insert some code here
                            } catch (IOException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getLocalizedMessage());
                    } finally {
                        if (udpSocket != null) {
                            udpSocket.close();
                        }
                    }
                }
            }, 0, RECEIVE_PERIOD);
        }
    }

    private static final long RECEIVE_PERIOD = 500;
    private static final long BROADCAST_PERIOD = 5000;

    private static final int UDP_PORT = 3012;
    private static final String BROADCAST_IP = "255.255.255.255";

    @Override
    public void run() {
        if (Thread.currentThread().isAlive()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();

                        byte[] buffer = new UDPMessage().toByteArray();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(BROADCAST_IP), UDP_PORT);
                        clientSocket.send(packet);

                        clientSocket.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }, 0, BROADCAST_PERIOD);
        }
    }
}
