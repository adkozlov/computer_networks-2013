package coursework.client.runnables;

import coursework.common.Configuration;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturersClientRunnable extends ClientRunnable {

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {

    }
}
