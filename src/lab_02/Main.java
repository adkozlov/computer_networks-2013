package lab_02;

import java.net.UnknownHostException;

/**
 * @author adkozlov
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException {
        new Thread(UDPAnnouncer.getInstance()).start();
        new Thread(TCPClient.getInstance()).start();
    }

}
