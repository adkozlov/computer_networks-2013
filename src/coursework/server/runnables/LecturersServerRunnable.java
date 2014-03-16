package coursework.server.runnables;

import coursework.common.Configuration;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturersServerRunnable extends ServerRunnable {
    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {

    }
}
