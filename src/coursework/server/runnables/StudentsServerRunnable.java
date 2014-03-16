package coursework.server.runnables;

import coursework.common.Configuration;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentsServerRunnable extends ServerRunnable {
    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {

    }
}