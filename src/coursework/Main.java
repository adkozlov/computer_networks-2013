package coursework;

import coursework.client.LecturerClient;
import coursework.client.StudentClient;
import coursework.server.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author adkozlov
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException {
        new Server(0).start();
        //new Server(1).start();

        InetAddress address = InetAddress.getLocalHost();
        new StudentClient(address).start();
        new LecturerClient(address).start();
    }
}
