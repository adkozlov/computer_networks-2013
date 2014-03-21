package coursework;

import coursework.client.LecturerClient;
import coursework.server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author adkozlov
 */
public class AnotherMain {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("192.168.1.36");

        new Server(address).start();
        new LecturerClient(address).start();
    }
}
