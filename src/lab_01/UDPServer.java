package lab_01;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UDPServer implements Runnable {

    private static final UDPServer INSTANCE = new UDPServer();

    public static UDPServer getInstance() {
        return INSTANCE;
    }

    private UDPServer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, Long>> iterator = servers.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, Long> entry = iterator.next();
                    if (System.currentTimeMillis() - entry.getValue().longValue() > PING) {
                        iterator.remove();
                    }
                }

                System.out.println(servers.keySet());
            }
        }, 0, PING);
    }

    public static final int PORT_NUMBER = 1234;
    private static final int BUFFER_LENGTH = 40;
    public static final long PING = 3 * UDPClient.PERIOD;

    Map<String, Long> servers = new HashMap<>();

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(PORT_NUMBER);

            while (true) {
                byte[] buffer = new byte[BUFFER_LENGTH];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                Message message = new Message(packet.getData());
                servers.put(message.getLastName().trim(), message.getTime());
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
