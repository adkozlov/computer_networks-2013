package lab_01;

import java.net.UnknownHostException;

public class Tester {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        new Thread(UDPServer.INSTANCE).start();

        final Thread apple = new Thread(new UDPClient("apple", "Kozlov"));
        apple.start();

        Thread.sleep(2 * UDPServer.PING);

        final Thread lenovo = new Thread(new UDPClient("lenovo", "Shagal"));
        lenovo.start();

        synchronized (apple) {
            apple.interrupt();
        }
        apple.join();
    }
}
