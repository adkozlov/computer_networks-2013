package lab_03;

/**
 * @author adkozlov
 */
public class Main {

    private static final Runnable[] SERVERS = new Runnable[]{UDPServer.getInstance()};

    public static void main(String[] args) {
        for (Runnable server : SERVERS) {
            new Thread(server).start();
        }
    }
}
