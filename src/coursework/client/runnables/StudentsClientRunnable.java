package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.*;
import coursework.common.model.Solution;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentsClientRunnable extends ClientRunnable {

    private final Solution solution;

    public StudentsClientRunnable(Solution solution) {
        this.solution = solution;
    }

    public StudentsClientRunnable(InetAddress address, int port, Solution solution) {
        super(address, port);
        this.solution = solution;
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        if (solution != null) {
            writeMessage(socket, new SolutionMessage(solution));
        }
    }

    @Override
    protected IMessage readMessage(byte[] bytes) throws IOException {
        byte type = bytes[Configuration.INT_BYTES_LENGTH];

        switch (type) {
            case TaskMessage.TYPE:
                return new TaskMessage(bytes);
            case VerdictMessage.TYPE:
                return new VerdictMessage(bytes);
        }

        throw new AbstractMessage.MessageTypeRecognizingException(type);
    }

    @Override
    protected String getFilePath() {
        return Configuration.STUDENT_FILES_PATH;
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}
