package coursework.client.runnables;

import coursework.common.Configuration;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentsClientRunnable extends ClientRunnable {

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {

    }
}
