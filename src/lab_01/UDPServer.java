package lab_01;

import java.io.IOException;
import java.net.*;
import java.util.*;


public class UDPServer implements Runnable {

    public static final UDPServer INSTANCE = new UDPServer();

    private UDPServer() {

    }

    public static final int PORT_NUMBER = 1234;
    public static final int BUFFER_LENGTH = 4 + 4 * 10 + 8 + 4 * 10 + 4 * 3;
    public static final long PING = 3 * UDPClient.PERIOD;

    Map<String, Long> servers = new HashMap<>();

    @Override
    public void run() {
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

        try {
            DatagramSocket socket = new DatagramSocket(PORT_NUMBER);

            byte[] buffer = new byte[BUFFER_LENGTH];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                byte[] data = packet.getData();
                String message = new String(data);

                StringTokenizer stringTokenizer = new StringTokenizer(message, "\u0000");
                stringTokenizer.nextToken();
                stringTokenizer.nextElement();

                Long time = Long.valueOf(stringTokenizer.nextToken());
                servers.put(stringTokenizer.nextToken().trim(), time);
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
