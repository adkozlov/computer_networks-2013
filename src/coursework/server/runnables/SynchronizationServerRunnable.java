package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.messages.*;
import coursework.common.model.SignedObject;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;
import coursework.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class SynchronizationServerRunnable extends AuthenticationServerRunnable {

    public SynchronizationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        byte[] bytes = readBytes(socket);

        IMessage message = readMessage(bytes);
        if (message instanceof TaskMessage) {
            Task task = ((TaskMessage) message).getTask();
            writeTask(task);
            getServer().addTask(task);
        } else if (message instanceof VerdictMessage) {
            Verdict verdict = ((VerdictMessage) message).getVerdict();
            writeVerdict(verdict);
            getServer().addVerdict(verdict);
        } else if (message instanceof SolutionMessage) {
            Solution solution = ((SolutionMessage) message).getSolution();
            writeSolution(solution);
            getServer().addSolution(solution);
        }

        writeAll(getServer().getServerConnection());
    }

    @Override
    protected IMessage readMessage(byte[] bytes) throws IOException {
        byte type = bytes[Configuration.INT_BYTES_LENGTH];

        switch (type) {
            case TaskMessage.TYPE:
                return new TaskMessage(bytes);
            case VerdictMessage.TYPE:
                return new VerdictMessage(bytes);
            case SolutionMessage.TYPE:
                return new SolutionMessage(bytes);
        }

        throw new AbstractMessage.MessageTypeRecognizingException(type);
    }

    @Override
    protected void writeAll(Connection connection) {
        writeTasks(connection);
        writeSolution(connection);
        writeVerdicts(connection);
    }

    @Override
    protected SynchronizationClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject) {
        return new SynchronizationClientRunnable(address, signedObject);
    }

    @Override
    protected int getPort() {
        return Configuration.SYNCHRONIZATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }

    public void synchronize(SignedObject signedObject) {
        newClientRunnable(getServer().getServerConnection().getAddress(), signedObject).start();
    }

    @Override
    protected String getFilePath() {
        return Configuration.SERVER_FILES_PATH;
    }
}
