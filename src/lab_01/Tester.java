package lab_01;

import java.net.UnknownHostException;

public class Tester {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        new Thread(UDPServer.getInstance()).start();

        final Thread apple = new Thread(new UDPClient("Kozlov"));
        apple.start();
    }
}
