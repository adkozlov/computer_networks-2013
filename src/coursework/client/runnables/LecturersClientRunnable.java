package coursework.client.runnables;

import coursework.common.Configuration;
import coursework.common.messages.SolutionMessage;
import coursework.common.messages.TaskMessage;
import coursework.common.messages.VerdictMessage;
import coursework.common.model.SignedObject;
import coursework.common.model.Task;
import coursework.common.model.Verdict;
import coursework.common.runnables.ClientRunnable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class LecturersClientRunnable extends ClientRunnable {

    private final SignedObject signedObject;

    public LecturersClientRunnable(InetAddress address, SignedObject signedObject) {
        super(address);
        this.signedObject = signedObject;
    }

    public LecturersClientRunnable(InetAddress address, int port, SignedObject signedObject) {
        super(address, port);
        this.signedObject = signedObject;
    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        if (signedObject instanceof Task) {
            writeMessage(socket, new TaskMessage((Task) signedObject));
        } else if (signedObject instanceof Verdict) {
            writeMessage(socket, new VerdictMessage((Verdict) signedObject));
        }
    }

    @Override
    protected SolutionMessage readMessage(byte[] bytes) throws IOException {
        return new SolutionMessage(bytes);
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
