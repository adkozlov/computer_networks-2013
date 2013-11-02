package lab_02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * @author adkozlov
 */
public class TCPClient implements Runnable {

    private static final TCPClient INSTANCE = new TCPClient();
    private static final int BUFFER_LENGTH = 4096;

    public static TCPClient getInstance() {
        return INSTANCE;
    }

    public static final int TCP_PORT = 1235;

    private List<TCPMessage> messages = new ArrayList<>();
    private Map<MacAddress, Host> hosts = new HashMap<>();
    private Map<MacAddress, Long> counts = new HashMap<>();

    private TCPClient() {
        new Messenger();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(UDPAnnouncer.UDP_PORT);

                    while (true) {
                        try {
                            byte[] buffer = new byte[BUFFER_LENGTH];
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);

                            UDPAnnounce announce = UDPAnnounce.fromByteArray(packet.getData());
                            hosts.put(announce.getMac(), new Host(announce, packet.getAddress()));

                            for (Host host : hosts.values()) {
                                System.out.println(host.getIp() + " " + host.getAnnounce().getMac());
                            }
                        } catch (IOException e) {
                            System.err.println(e.getLocalizedMessage());
                        }
                    }
                } catch (SocketException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }, 0, UDPAnnouncer.PERIOD);
    }

    private void sendMessages(MacAddress mac) {

    }

    @Override
    public void run() {

    }

    private class Host {
        private final UDPAnnounce announce;
        private final long offset;
        private final InetAddress ip;

        private Host(UDPAnnounce announce, InetAddress ip) {
            this.announce = announce;
            this.offset = announce.getCurrentTime() - System.currentTimeMillis();
            this.ip = ip;
        }

        private UDPAnnounce getAnnounce() {
            return announce;
        }

        private long getOffset() {
            return offset;
        }

        private InetAddress getIp() {
            return ip;
        }
    }
}
