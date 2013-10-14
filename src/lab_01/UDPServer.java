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
        }, 0, PERIOD);
    }

    public static final int PORT_NUMBER = 1234;
    public static final long PING = 3 * UDPClient.PERIOD;
    private static final long PERIOD = UDPClient.PERIOD / 5;

    Map<String, Long> servers = new HashMap<>();

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(PORT_NUMBER);
        } catch (SocketException e) {
            System.out.println(e.getLocalizedMessage());
        }

        while (true) {
            try {
                byte[] buffer = new byte[Message.BUFFER_LENGTH];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                Message message = new Message(packet.getData());
                servers.put(message.getLastName().trim(), message.getTime());
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }
}
