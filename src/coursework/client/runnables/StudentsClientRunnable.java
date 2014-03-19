package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.SolutionMessage;
import coursework.common.model.Solution;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentsClientRunnable extends ClientRunnable {

    private final Solution solution;

    public StudentsClientRunnable(Solution solution) {
        this.solution = solution;
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        writeMessage(socket, new SolutionMessage(solution));
    }
}
