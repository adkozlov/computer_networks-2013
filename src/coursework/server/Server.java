package coursework.server;

import coursework.common.Utils;
import coursework.server.runnables.AuthenticationServerRunnable;
import coursework.server.runnables.LecturersServerRunnable;
import coursework.server.runnables.ServerRunnable;
import coursework.server.runnables.StudentsServerRunnable;

/**
 * @author adkozlov
 */
public class Server implements Runnable {

    public static void main(String[] args) {
        Utils.startRunnable(new Server());
    }

    private final ServerRunnable[] SERVERS = {new AuthenticationServerRunnable(), new StudentsServerRunnable(), new LecturersServerRunnable()};

    @Override
    public void run() {
        Utils.startRunnables(SERVERS);
    }
}
