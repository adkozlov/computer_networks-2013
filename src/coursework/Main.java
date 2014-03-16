package coursework;

import coursework.client.LecturerClient;
import coursework.client.StudentClient;
import coursework.server.Server;

/**
 * @author adkozlov
 */
public class Main {

    public static void main(String[] args) {
        Server.main(args);
        StudentClient.main(args);
        LecturerClient.main(args);
    }
}
