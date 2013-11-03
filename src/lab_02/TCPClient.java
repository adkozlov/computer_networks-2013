package lab_02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.*;

/**
 * @author adkozlov
 */
public class TCPClient implements Runnable {

    private static final TCPClient INSTANCE = new TCPClient();
    private static final int BUFFER_LENGTH = 4096;
    private static final long PERIOD = 500;

    public static TCPClient getInstance() {
        return INSTANCE;
    }

    public static final int TCP_PORT = 1235;

    private List<TCPMessage> myMessages = new ArrayList<>();
    private SortedSet<TCPMessage> messages = new TreeSet<>();
    private Map<MacAddress, Integer> counts = new HashMap<>();
    private Map<MacAddress, Long> offsets = new HashMap<>();

    public SortedSet<TCPMessage> getMessages() {
        return Collections.unmodifiableSortedSet(messages);
    }

    private TCPClient() {
        Messenger.getInstance();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DatagramSocket udpSocket = null;
                try {
                    udpSocket = new DatagramSocket(UDPAnnouncer.UDP_PORT);

                    while (true) {
                        byte[] buffer = new byte[BUFFER_LENGTH];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        udpSocket.receive(packet);

                        UDPAnnounce announce = UDPAnnounce.fromByteArray(packet.getData());
                        sendMessages(packet.getAddress(), announce.getMac());
                        offsets.put(announce.getMac(), System.currentTimeMillis() - announce.getCurrentTime());
                    }
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                } finally {
                    if (udpSocket != null) {
                        udpSocket.close();
                    }
                }
            }
        }, 0, PERIOD);
    }

    private void sendMessages(InetAddress ip, MacAddress mac) throws IOException {
        for (int i = counts.containsKey(mac) ? counts.get(mac) : 0; i < myMessages.size(); i++) {
            Socket socket = new Socket(ip, TCP_PORT);
            OutputStream os = socket.getOutputStream();

            os.write(myMessages.get(i).toByteArray());

            socket.close();
            counts.put(mac, i + 1);
        }
    }

    public void sendMessage(String message) {
        try {
            myMessages.add(new TCPMessage(message));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(TCP_PORT);

            while (true) {
                if (!offsets.isEmpty()) {
                    Socket socket = serverSocket.accept();
                    InputStream is = socket.getInputStream();

                    byte[] buffer = new byte[BUFFER_LENGTH];
                    is.read(buffer);

                    TCPMessage tcpMessage = TCPMessage.fromByteArray(buffer);
                    messages.add(new TCPMessage(tcpMessage, offsets.get(tcpMessage.getMac())));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }
}
