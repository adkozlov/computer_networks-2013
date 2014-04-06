package coursework.server.runnables;

import coursework.common.*;
import coursework.common.messages.*;
import coursework.common.model.SignedObject;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;
import coursework.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author adkozlov
 */
public class SynchronizationServerRunnable extends AuthenticationServerRunnable {

    public SynchronizationServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected String getFilePath(Signature signature) {
        return String.format(Configuration.SERVER_FILES_PATH_FORMAT, UsersContainer.getInstance().getLogin(signature));
    }

    @Override
    protected Path buildTaskFilePath(String name, long deadline, Signature signature) {
        return Paths.get(String.format(Configuration.TASK_FILE_FORMAT, getFilePath(signature), Utils.longToDateString(deadline), name));
    }

    @Override
    protected Path buildVerdictFilePath(String studentName, String taskName, boolean accepted, Signature signature) {
        return Paths.get(String.format(Configuration.VERDICT_FILE_FORMAT, getFilePath(signature), taskName, studentName, Utils.nowToDateString(), accepted));
    }

    @Override
    protected Path buildSolutionFilePath(String fileName, Signature signature, String taskName, String courseName) {
        return Paths.get(String.format(Configuration.SOLUTION_FILE_FORMAT, getFilePath(signature), courseName, taskName, Utils.nowToDateString(), fileName));
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        byte[] bytes = readBytes(socket);

        IMessage message = readMessage(bytes);
        if (message instanceof TaskMessage) {
            Task task = ((TaskMessage) message).getTask();
            writeTask(task);
            getServer().addTask(task, true);
        } else if (message instanceof VerdictMessage) {
            Verdict verdict = ((VerdictMessage) message).getVerdict();
            writeVerdict(verdict);
            getServer().addVerdict(verdict, true);
        } else if (message instanceof SolutionMessage) {
            Solution solution = ((SolutionMessage) message).getSolution();
            writeSolution(solution);
            getServer().addSolution(solution, true);
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
        writeSolutions(connection);
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
}
