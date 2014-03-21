package coursework;

import coursework.client.StudentClient;
import coursework.server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author adkozlov
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("192.168.1.35");

        new Server(0, address).start();
        //new Server(1).start();


        new StudentClient(address).start();
        //new LecturerClient(address).start();
    }
}
