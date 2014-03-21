package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.Signature;
import coursework.common.UsersContainer;
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
    public void run() {
        super.run();
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

        Signature signature = UsersContainer.getInstance().getSignature(getFilePath());
        writeAll(socket.getInetAddress(), signature);
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
    protected void writeAll(InetAddress address, Signature signature) {
        writeTasks(address, signature);
        writeSolution(address, signature);
        writeVerdicts(address, signature);
    }

    @Override
    protected SynchronizationClientRunnable newClientRunnable(InetAddress address, SignedObject signedObject) {
        return new SynchronizationClientRunnable(address, getServer().getServerId(), signedObject);
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
        newClientRunnable(getServer().getAnotherServerAddress(), signedObject);
    }
}
