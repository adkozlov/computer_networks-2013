package lab_01;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class DiscoveryThread implements Runnable {

    public static DiscoveryThread getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {

        private static final DiscoveryThread INSTANCE = new DiscoveryThread();
    }

    DatagramSocket socket;

    @Override
    public void run() {
        try {
//Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = new DatagramSocket(8888, InetAddress.getByName("127.0.0.1"));
            socket.setBroadcast(true);
            final HashMap<String, Long> messages = new HashMap<String, Long>();
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long ct = System.currentTimeMillis();
                    HashMap<String, Long> me = (HashMap<String, Long>) messages.clone();
                    for(Map.Entry<String, Long> entry : me.entrySet()){
                        if(ct - entry.getValue() < 2000){
                            messages.remove(entry.getKey());
                        }
                    }
                }
            }, 0, 1000);
            while (true) {
                System.out.println(getClass().getName() + "»>Ready to receive broadcast packets!");

//Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                String message = new String(packet.getData()).trim();

                StringTokenizer stringTokenizer = new StringTokenizer(message, "|");

                String addr = stringTokenizer.nextToken();
                String time = stringTokenizer.nextToken();
                String surname =stringTokenizer.nextToken();

                System.err.println(addr + " " + time + " " + surname);

                messages.put(addr, Long.valueOf(time));

            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Thread(getInstance()).start();
    }

}