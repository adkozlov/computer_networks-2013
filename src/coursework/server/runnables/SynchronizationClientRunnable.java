package coursework.server.runnables;

import coursework.client.runnables.AuthenticationClientRunnable;
import coursework.common.Configuration;
import coursework.common.messages.*;
import coursework.common.model.SignedObject;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class SynchronizationClientRunnable extends AuthenticationClientRunnable {

    private final SignedObject signedObject;

    public SynchronizationClientRunnable(InetAddress address, SignedObject signedObject) {
        super(address, Configuration.SYNCHRONIZATION_PORT);
        this.signedObject = signedObject;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        IMessage message;

        if (signedObject instanceof Task) {
            message = new TaskMessage((Task) signedObject);
        } else if (signedObject instanceof Solution) {
            message = new SolutionMessage((Solution) signedObject);
        } else {
            message = new VerdictMessage((Verdict) signedObject);
        }

        writeMessage(socket, message);
    }

    @Override
    protected AuthenticationResponseMessage readMessage(byte[] bytes) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected int getPort() {
        return Configuration.SYNCHRONIZATION_PORT;
    }

    @Override
    protected String getFilePath() {
        return Configuration.SERVER_FILES_PATH;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}
