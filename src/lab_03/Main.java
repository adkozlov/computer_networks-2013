package lab_03;

/**
 * @author adkozlov
 */
public class Main {

    public static void main(String[] args) {
        new Thread(UDPServer.getInstance()).start();
    }
}
