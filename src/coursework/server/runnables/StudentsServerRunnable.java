package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.messages.SolutionMessage;

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
        writeSolution(readMessage(readBytes(socket)).getSolution());
    }

    @Override
    protected SolutionMessage readMessage(byte[] bytes) throws IOException {
        return new SolutionMessage(bytes);
    }
}